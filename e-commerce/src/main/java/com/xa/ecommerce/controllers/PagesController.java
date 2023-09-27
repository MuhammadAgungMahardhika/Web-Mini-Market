package com.xa.ecommerce.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.DocumentException;
import com.xa.ecommerce.exporters.ProductExporter;
import com.xa.ecommerce.models.Products;
import com.xa.ecommerce.services.ProductService;

@Controller
@RequestMapping("/pages")
public class PagesController {
    @GetMapping("/lookup")
    public ModelAndView lookupindex() {
        ModelAndView view = new ModelAndView("/lookup/index");
        return view;
    }

    @GetMapping("/store/category")
    public ModelAndView storeCategory() {
        ModelAndView view = new ModelAndView("/store/category");
        return view;
    }

    @GetMapping("/store/product")
    public ModelAndView storeProduct() {
        ModelAndView view = new ModelAndView("/store/product");
        return view;
    }

    @Autowired
    ProductService productService;

    @GetMapping("/store/product/pdf")
    public void exportToPdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyymmmddHHmmss");
        String filename = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headerValue = "attachment; filename=product" + filename + ".pdf";
        response.setHeader(headerkey, headerValue);

        List<Products> listProduct = productService.allProduct();

        ProductExporter exporter = new ProductExporter(listProduct);
        exporter.export(response);

    }

    @GetMapping("/order")
    public ModelAndView order() {
        ModelAndView view = new ModelAndView("/order/index");
        return view;
    }

    @GetMapping("/payment/{cart_id}")
    public ModelAndView payment() {
        ModelAndView view = new ModelAndView("/payment/index");
        return view;
    }
}
