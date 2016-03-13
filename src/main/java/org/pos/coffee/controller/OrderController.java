package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.*;
import org.pos.coffee.bean.helper.OrderHelper;
import org.pos.coffee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Laurie on 11/14/2015.
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseCrudController<Order> {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileDetailService fileDetailService;

    @Autowired
    private ReferenceLookUpService referenceLookUpService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ListPriceService listPriceService;

    @Autowired
    private PriceSetService priceSetService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ProductGroupService productGroupService;


    @RequestMapping(value = "/getAllProduct", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getAllProductDetails() throws Exception{
        Map<String,Object> returnMap = new HashMap<>();

        Product getAll = new Product();
        getAll.setIsActive(true);
        getAll.setIsDisplayOnOrder(true);
        List<Product> results = productService.findEntity(getAll);
        List<Product> allProduct = new ArrayList<>();
        for(Product product: results){

            if(product.getProductGroup()!=null){
                product.setProductGroup(productGroupService.load(product.getProductGroupId()));
            }

            if(product.getPromoGroupList() != null){
                for(ReferenceLookUp group: product.getPromoGroupList()) {
                    group = referenceLookUpService.load(group.getId());
                }
            }

            if(product.getProductUnder() != null){
                for(Product productUnder: product.getProductUnder()){
                    productUnder = productService.load(productUnder.getId());
                    if(productUnder.getSizeId()!=null){
                        productUnder.setSize(referenceLookUpService.load(productUnder.getSizeId()));
                    }

                    if(productUnder.getPrice()!=null){
                        productUnder.setPrice(listPriceService.load(productUnder.getPrice().getId()));
                    }

                    if(productUnder.getIngredientList()!=null &&
                            productUnder.getIngredientList().size()>0){
                        for(Ingredient ingredient: productUnder.getIngredientList()){
                            ingredient = ingredientService.load(ingredient.getId());
                        }
                    }
                    allProduct.add(productUnder);
                }
            }

            if(product.getIngredientList()!=null &&
                    product.getIngredientList().size()>0){
                for(Ingredient ingredient: product.getIngredientList()){
                    ingredient = ingredientService.load(ingredient.getId());
                }
            }

            if(product.getPrice()!=null){
                product.setPrice(listPriceService.load(product.getPrice().getId()));
            }
            allProduct.add(product);

        }

        returnMap.put("results", results);
        returnMap.put("all", allProduct);
        return returnMap;
    }

    @RequestMapping(value = "/getPriceSet", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> getPriceSetTriggered(@RequestBody OrderHelper[] orderHelperList) throws Exception{

        List<Order> orderList = new ArrayList<>();
        double total = 0;
        double totalDiscount = 0;
        double totalSurcharge = 0;
        for(OrderHelper orderHelper: orderHelperList){
            get_log().warn(orderHelper.getProductId()+" productId");
            if(orderHelper.getProductId()!=null){
                List<PriceSet> priceSetList = priceSetService.getPossiblePriceSets(orderHelper);
                List<PriceSet> applyPriceSetList = priceSetService.getApplicablePriceSets(priceSetList, orderHelper);

                double subtotal = orderHelper.getQuantity() * orderHelper.getPrice();
                double gross = subtotal;
                double totalLineDiscount = 0;
                double totalLineSurcharge = 0;
                for(PriceSet priceSet: applyPriceSetList) {
                    get_log().warn(subtotal+" prior priceset");
                    if(priceSet.getIsDiscount()){
                        double discount = 0;
                        if(priceSet.getIsPercentage()){
                            DecimalFormat df = new DecimalFormat("#.##");
                            df.setRoundingMode(RoundingMode.DOWN);
                            discount = Double.valueOf(df.format(subtotal * (priceSet.getPriceSetModifier()/100)));
                            gross -= discount;
                        } else {
                            discount = priceSet.getPriceSetModifier();
                            gross -= discount;
                        }
                        totalLineDiscount += discount;
                        totalDiscount += discount;
                    } else {
                        double surcharge = 0;
                        if(priceSet.getIsPercentage()){
                            DecimalFormat df = new DecimalFormat("#.##");
                            df.setRoundingMode(RoundingMode.UP);
                            surcharge = Double.valueOf(df.format(subtotal * (priceSet.getPriceSetModifier()/100)));
                            gross += surcharge;
                        } else {
                            surcharge = priceSet.getPriceSetModifier();
                            gross += surcharge;
                        }
                        totalLineSurcharge += surcharge;
                        totalSurcharge += surcharge;
                    }
                    get_log().warn(subtotal+" after price set");
                }

                Double totalAddOnPrice = 0D;
                for(AddOn addOn: orderHelper.getAddOn()){
                    totalAddOnPrice += addOn.getPrice() * addOn.getQuantity();
                }
                Order orderLine = new Order();
                orderLine.setProduct(orderHelper.getProduct());
                orderLine.setQuantity(orderHelper.getQuantity());
                orderLine.setTotalLinePrice(subtotal);
                orderLine.setAddOnList(orderHelper.getAddOn());
                orderLine.setAppliedPriceSet(applyPriceSetList);

                if(orderHelper.getListId()!=null){
                    orderLine.setListPrice(listPriceService.load(orderHelper.getListId()));
                }

                orderLine.setTotalPriceSetDisc(totalLineDiscount);
                orderLine.setTotalPriceSetSur(totalLineSurcharge);
                orderLine.setGrossLinePrice(gross+totalAddOnPrice);

                orderList.add(orderLine);
                total += gross + totalAddOnPrice;
            }
        }


        List<PriceSet> saleLevelPriceSet = priceSetService.getPossibleSalePriceSets();
        Sale sale = new Sale();
        if(saleLevelPriceSet != null &&
                saleLevelPriceSet.size()>0){
            sale = priceSetService.applyPriceSets(saleLevelPriceSet, total, totalDiscount, totalSurcharge);
        } else {
            sale.setTotalSale(total);
            sale.setTotalDiscount(totalDiscount);
            sale.setTotalSurcharge(totalSurcharge);
        }
        sale.setOrders(orderList);
        sale.setGrossTotalLinePrice(total);

        ReferenceLookUp applyTax = referenceLookUpService.getReferenceLookUpByKey("SETTING_APPLY_VAT");
        if(applyTax!=null &&
                "TRUE".equals(applyTax.getValue())){

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.HALF_EVEN);

            double taxRate = applyTax.getNumberValue().doubleValue()/100;
            sale.setPreTax(Double.parseDouble(df.format(sale.getTotalSale()/(taxRate+1))));
            sale.setTax(Double.parseDouble(df.format(sale.getTotalSale() - (sale.getTotalSale() / (taxRate + 1)))));
            sale.setTaxRate(applyTax.getNumberValue()+"%");
        }

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("results", sale);
        returnMap.put("message", "success");
        returnMap.put("status", true);
        returnMap.put("taxable", applyTax.getValue());

        return returnMap;
    }

}
