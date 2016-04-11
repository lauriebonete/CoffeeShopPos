package org.evey.service.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.evey.service.ReceiptPDFService;
import org.evey.utility.FileUtil;
import org.pos.coffee.bean.*;
import org.pos.coffee.service.FileDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Laurie on 3/12/2016.
 */
@Service("pdfBoxImpl")
public class ReceiptPDFServicePDFBoxImpl implements ReceiptPDFService {

    @Value("${receipt.folder}")
    private String filePath;

    @Autowired
    private FileDetailService fileDetailService;

    @Override
    public FileDetail generateReceiptPDF(Sale sale){
        PDRectangle rec = new PDRectangle(200, 630);
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(rec);
        document.addPage(page);

        PDPageContentStream contentStream = null;

        try {
            contentStream = new PDPageContentStream(document, page,true,true);

            String fileName = FileUtil.generateFilePath(sale.getSaleCode() + ".pdf", filePath);
            drawTable(page, contentStream, sale);
            contentStream.close();
            /*document.save(filePath+"/"+sale.getSaleCode()+".pdf");*/
            document.save(fileName);
            document.close();

            FileDetail receipt = new FileDetail();
            receipt.setFileName(sale.getSaleCode());
            receipt.setFilePath(fileName);
            receipt.setIsActive(true);
            receipt.setFileType("pdf");
            fileDetailService.save(receipt);
            return  receipt;

        } catch (Exception e){
            e.printStackTrace();
        }

        return new FileDetail();
    }

    private static void drawTable(PDPage page, PDPageContentStream contentStream, Sale sale) {
        try {
            PDFont font = PDType1Font.HELVETICA_BOLD;
            contentStream.setFont(font, 6 );

            contentStream.beginText();
            contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth()-(font.getStringWidth(sale.getSaleCode()) / 1000 * 6))/2, 605);
            contentStream.drawString(sale.getSaleCode());
            contentStream.endText();

            contentStream.beginText();
            contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth()-(font.getStringWidth(new SimpleDateFormat("MM/dd/yyyy '-' hh:mma").format(sale.getSaleDate())) / 1000 * 6))/2, 590);
            contentStream.drawString(new SimpleDateFormat("MM/dd/yyyy '-' hh:mma").format(sale.getSaleDate()));
            contentStream.endText();

            float y = 575;
            float rowHeight = 15f;

            contentStream.beginText();
            contentStream.moveTextPositionByAmount(15, y);
            contentStream.drawString("QTY");
            contentStream.endText();

            contentStream.beginText();
            contentStream.moveTextPositionByAmount(30, y);
            contentStream.drawString("Product");
            contentStream.endText();

            contentStream.beginText();
            contentStream.moveTextPositionByAmount(145, y);
            contentStream.drawString("Price");
            contentStream.endText();

            contentStream.beginText();
            contentStream.moveTextPositionByAmount(175, y);
            contentStream.drawString("Total");
            contentStream.endText();

            y-= rowHeight;

            if(sale.getOrders()!=null){
                for(Order order: sale.getOrders()){

                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(15, y);
                    contentStream.drawString(order.getQuantity()!=null ? order.getQuantity().toString():"");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(30, y);
                    contentStream.drawString(order.getProduct() != null ? order.getProduct().getProductName():"");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(145, y);
                    contentStream.drawString(order.getListPrice()!=null ? order.getListPrice().getPrice().toString():"");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(175, y);
                    contentStream.drawString(order.getTotalLinePrice()!=null ? order.getTotalLinePrice().toString():"");
                    contentStream.endText();

                    for(AddOn addOn: order.getAddOnList()){
                        y-=rowHeight;
                        contentStream.beginText();
                        contentStream.moveTextPositionByAmount(15, y);
                        contentStream.drawString(addOn.getQuantity().toString());
                        contentStream.endText();

                        contentStream.beginText();
                        contentStream.moveTextPositionByAmount(30, y);
                        contentStream.drawString(addOn.getProduct().getProductName());
                        contentStream.endText();

                        contentStream.beginText();
                        contentStream.moveTextPositionByAmount(145, y);
                        contentStream.drawString(addOn.getPrice().toString());
                        contentStream.endText();

                        contentStream.beginText();
                        contentStream.moveTextPositionByAmount(175, y);
                        contentStream.drawString(Double.valueOf(addOn.getPrice() * addOn.getQuantity()).toString());
                        contentStream.endText();
                    }

                    if(order.getAppliedPriceSet() != null){
                        for(PriceSet priceSet: order.getAppliedPriceSet()){
                            y-=rowHeight;

                            contentStream.beginText();
                            contentStream.moveTextPositionByAmount(30, y);
                            contentStream.drawString(priceSet.getPriceSetName());
                            contentStream.endText();

                            contentStream.beginText();
                            contentStream.moveTextPositionByAmount(145, y);
                            contentStream.drawString(getPriceSetEffect(priceSet));
                            contentStream.endText();

                            y-=rowHeight;

                            contentStream.beginText();
                            contentStream.moveTextPositionByAmount(145, y);
                            contentStream.drawString("Sub-total");
                            contentStream.endText();

                            contentStream.beginText();
                            contentStream.moveTextPositionByAmount(175, y);
                            contentStream.drawString(order.getGrossLinePrice().toString());
                            contentStream.endText();
                        }
                    }
                    y-=rowHeight;
                }
            }

            contentStream.beginText();
            contentStream.moveTextPositionByAmount(145, y);
            contentStream.drawString("Total");
            contentStream.endText();

            contentStream.beginText();
            contentStream.moveTextPositionByAmount(175, y);
            contentStream.drawString(sale.getGrossTotalLinePrice().toString());
            contentStream.endText();

            y-=rowHeight;

            if(sale.getAppliedPriceSet() != null){
                for(PriceSet priceSet: sale.getAppliedPriceSet()){
                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(30, y);
                    contentStream.drawString(priceSet.getPriceSetName());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(145, y);
                    contentStream.drawString(getPriceSetEffect(priceSet));
                    contentStream.endText();

                    y-=rowHeight;
                }
            }

            if(sale.getTaxRate()!=null){
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(30, y);
                contentStream.drawString("TAX");
                contentStream.endText();

                contentStream.beginText();
                contentStream.moveTextPositionByAmount(145, y);
                contentStream.drawString(sale.getTaxRate());
                contentStream.endText();

                contentStream.beginText();
                contentStream.moveTextPositionByAmount(175, y);
                contentStream.drawString(sale.getTax().toString());
                contentStream.endText();
            }

            PDFont fontTotal = PDType1Font.HELVETICA_BOLD;
            contentStream.setFont(fontTotal, 8);

            y-=rowHeight+10;

            contentStream.beginText();
            contentStream.moveTextPositionByAmount(110, y);
            contentStream.drawString("Grand Total");
            contentStream.endText();

            contentStream.beginText();
            contentStream.moveTextPositionByAmount(165, y);
            contentStream.drawString(sale.getTotalSale().toString());
            contentStream.endText();
        }
        catch ( IOException ioe )
        {
            //Package.log.error( " drawTable :" + ioe);
            final String errormsg = "Could not drawTable ";
            //Package.log.error("In RuleThread drawTable " + errormsg, ioe);
            throw new RuntimeException(errormsg, ioe);
        }
        catch ( Exception ex )
        {
            //Package.log.error( " drawTable :" + ex);
            final String errormsg = "Could not drawTable ";
            //Package.log.error("In RuleThread drawTable " + errormsg, ex);
            throw new RuntimeException(errormsg, ex);
        }
    }

    private static String getPriceSetEffect(PriceSet priceSet){
        StringBuilder effectBuilder = new StringBuilder();

        if(priceSet.getIsDiscount()){
            effectBuilder.append("LESS ");
        } else {
            effectBuilder.append("ADD ");
        }
        effectBuilder.append(priceSet.getPriceSetModifier());
        if(priceSet.getIsPercentage()){
            effectBuilder.append("%");
        }else {
            effectBuilder.append("php");
        }

        return effectBuilder.toString();
    }



}
