package org.evey.service;

import org.pos.coffee.bean.Sale;

/**
 * Created by Laurie on 3/12/2016.
 */
public interface ReceiptPDFService {
    public void generateReceiptPDF(Sale sale);
}
