package com.test.hplus.controllers;

import com.test.hplus.beans.Product;
import com.test.hplus.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SearchController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    @GetMapping("/searchProduct")
    public DeferredResult<String> search(@RequestParam("searchParam") String searchParam,
                                         Model model, HttpServletRequest request) {
        DeferredResult<String> result = new DeferredResult<>();

        LOGGER.debug("in search controller");
        LOGGER.debug("searchParam: {}", searchParam);
        LOGGER.debug("Async supported: " + request.isAsyncSupported());
        LOGGER.debug("Name of servlet request thread: " + Thread.currentThread().getName());

        taskExecutor.execute(() -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.debug("Name of servlet request thread: " + Thread.currentThread().getName());
            List<Product> products = productRepository.searchByName(searchParam);
            model.addAttribute("products", products);

            result.setResult("search");
        });

        return result;
    }
}
