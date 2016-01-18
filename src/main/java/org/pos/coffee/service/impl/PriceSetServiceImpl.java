package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.*;
import org.pos.coffee.bean.helper.OrderHelper;
import org.pos.coffee.service.PriceSetService;
import org.pos.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Laurie on 12/7/2015.
 */
@Service("priceSetService")
public class PriceSetServiceImpl extends BaseCrudServiceImpl<PriceSet> implements PriceSetService {

    @Autowired
    private ProductService productService;

    @Override
    public List<PriceSet> getPossiblePriceSets(OrderHelper orderHelper) throws Exception {
        Set<Product> products = new HashSet<>();
        Product product = new Product();
        product.setId(orderHelper.getProductId());
        products.add(product);

        PriceSet lookForProduct = new PriceSet();
        lookForProduct.setLookForEndDate(new Date());
        lookForProduct.setLookForStartDate(new Date());
        lookForProduct.setIsActive(true);
        lookForProduct.setProduct(products);
        lookForProduct.setIsForLine(true);

        List<PriceSet> priceSetList = new ArrayList<>();
        priceSetList = getDao().findEntity(lookForProduct);
        if(priceSetList.size() <=0){
            Product thisProduct = productService.load(orderHelper.getProductId());

            PriceSet lookForPromoGroup = new PriceSet();
            lookForPromoGroup.setLookForEndDate(new Date());
            lookForPromoGroup.setLookForStartDate(new Date());
            lookForPromoGroup.setIsActive(true);
            lookForPromoGroup.setIsForLine(true);

            Set<ReferenceLookUp> promoGroup = new HashSet<>();
            for(ReferenceLookUp group: thisProduct.getPromoGroupList()){
                ReferenceLookUp lookForThisGroup = new ReferenceLookUp();
                lookForThisGroup.setId(group.getId());
                promoGroup.add(lookForThisGroup);
            }
            lookForPromoGroup.setPromoGroup(promoGroup);
            if(promoGroup.size()>0){
                List<PriceSet> priceSetListPromo = getDao().findEntity(lookForPromoGroup);
                priceSetList.addAll(priceSetListPromo);
            }
        }

        return priceSetList;
    }

    @Override
    public List<PriceSet> getApplicablePriceSets(List<PriceSet> priceSetList, OrderHelper orderHelper) {
        List<PriceSet> toBeApplied = new ArrayList<>();
        for(PriceSet priceSet: priceSetList){
            //check if priceset is looking for rules or just apply it right away
            if((priceSet.getIsPriceTriggered() != null && priceSet.getIsPriceTriggered()) ||
                    (priceSet.getIsQuantityTriggered() != null && priceSet.getIsQuantityTriggered())) {
                if(priceSet.getIsPriceTriggered() != null &&
                        priceSet.getIsPriceTriggered()){
                    if((orderHelper.getPrice() * orderHelper.getQuantity()>= priceSet.getMinPrice())){
                        //apply this priceset on order line
                        toBeApplied.add(priceSet);
                        if(stopApplyingOtherPriceSet(priceSet)){
                            break;
                        }
                    }

                } else {
                    if(orderHelper.getQuantity()>= priceSet.getMinQuantity()) {
                        //apply this priceset on order line
                        toBeApplied.add(priceSet);
                        if(stopApplyingOtherPriceSet(priceSet)){
                            break;
                        }
                    }
                }
            } else {
                //apply it right away
                toBeApplied.add(priceSet);
                if(stopApplyingOtherPriceSet(priceSet)){
                    break;
                }
            }

        }
        return toBeApplied;
    }

    @Override
    public List<PriceSet> getPossibleSalePriceSets() throws Exception{

        PriceSet lookFor = new PriceSet();
        lookFor.setIsForLine(false);
        lookFor.setLookForStartDate(new Date());
        lookFor.setLookForEndDate(new Date());
        lookFor.setIsActive(true);

        List<PriceSet> results = getDao().findEntity(lookFor);
        return results;
    }

    @Override
    public Sale applyPriceSets(List<PriceSet> priceSetList, Double total, Double totalDiscount, Double totalSurcharge) {
        Sale sale = new Sale();
        List<PriceSet> appliedPriceSet = new ArrayList<>();
        for(PriceSet priceSet: priceSetList){
            if(priceSet.getIsPriceTriggered() != null &&
                    priceSet.getIsPriceTriggered()){
                if(priceSet.getMinPrice()<=total){
                    if(priceSet.getIsDiscount() != null &&
                            priceSet.getIsDiscount()){

                        double discount = 0;
                        if(priceSet.getIsPercentage() != null &&
                                priceSet.getIsPercentage()){
                            discount = total  * (priceSet.getPriceSetModifier()/100);
                            total -= discount;
                        } else {
                            discount = priceSet.getPriceSetModifier();
                            total -= discount;
                        }
                        totalDiscount += discount;
                    } else {
                        double surcharge = 0;
                        if(priceSet.getIsPercentage() != null &&
                                priceSet.getIsPercentage()){
                            surcharge = total  * (priceSet.getPriceSetModifier()/100);
                            total -= surcharge;
                        } else {
                            surcharge = priceSet.getPriceSetModifier();
                            total -= surcharge;
                        }
                        totalSurcharge += surcharge;
                    }

                    if(priceSet.getStopOtherPriceSet() != null &&
                            priceSet.getStopOtherPriceSet()){
                        break;
                    }
                    appliedPriceSet.add(priceSet);
                }
            }
        }
        sale.setTotalSurcharge(totalSurcharge);
        sale.setTotalDiscount(totalDiscount);
        sale.setAppliedPriceSet(appliedPriceSet);
        sale.setTotalSale(total);
        return sale;
    }

    private boolean stopApplyingOtherPriceSet(PriceSet priceSet){
        if(priceSet.getStopOtherPriceSet() != null &&
                priceSet.getStopOtherPriceSet()) {
            return true;
        }
        return false;
    }
}
