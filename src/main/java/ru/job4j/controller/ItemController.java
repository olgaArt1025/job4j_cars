package ru.job4j.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.model.Item;
import ru.job4j.model.User;
import ru.job4j.service.ItemService;
import ru.job4j.service.CarService;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class ItemController {
    private final ItemService service;
    private final CarService carService;

    public ItemController(ItemService service, CarService carService) {
        this.service = service;
        this.carService = carService;
    }

    @GetMapping("/items")
    public String ads(Model model, HttpSession session) {
        findUser(model, session);
        model.addAttribute("items", service.findAll());
        model.addAttribute("bodies", carService.findAllBody());
        model.addAttribute("brands", carService.findAllBrand());
        model.addAttribute("categories", carService.findAllCategory());
        return "items";
    }

    @GetMapping("/items/brand/{brandId}")
    public String itemBrand(Model model, @PathVariable("brandId") int brandId, HttpSession session) {
        findUser(model, session);
        model.addAttribute("brands", carService.findAllBrand());
        model.addAttribute("bodies", carService.findAllBody());
        model.addAttribute("categories", carService.findAllCategory());
        model.addAttribute("items", service.findByBrand(brandId));
        return "items";
    }

    @GetMapping("/items/body/{bodyId}")
    public String itemBody(Model model, @PathVariable("bodyId") int bodyId, HttpSession session) {
        findUser(model, session);
        model.addAttribute("brands", carService.findAllBrand());
        model.addAttribute("bodies", carService.findAllBody());
        model.addAttribute("categories", carService.findAllCategory());
        model.addAttribute("items", service.findByBody(bodyId));
        return "items";
    }

    @GetMapping("/items/category/{categoryId}")
    public String itemCategory(Model model, @PathVariable("categoryId") int categoryId, HttpSession session) {
        findUser(model, session);
        model.addAttribute("brands", carService.findAllBrand());
        model.addAttribute("bodies", carService.findAllBody());
        model.addAttribute("categories", carService.findAllCategory());
        model.addAttribute("items", service.findByCategory(categoryId));
        return "items";
    }

    @GetMapping("/formAddItems")
    public String formAddItems(Model model) {
        model.addAttribute("categories", carService.findAllCategory());
        model.addAttribute("brands", carService.findAllBrand());
        model.addAttribute("bodies", carService.findAllBody());
        return "addItems";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item,
                             @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        item.setUser((User) session.getAttribute("user"));
        item.setPhoto(file.getBytes());
        service.add(item);
        return "redirect:/items";
    }

    @GetMapping("/formUpdateItem/{itemId}")
    public String formUpdateItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", service.findById(id));
        model.addAttribute("brands", carService.findAllBrand());
        model.addAttribute("bodies", carService.findAllBody());
        model.addAttribute("categories", carService.findAllCategory());
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item,
                             @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        item.setUser((User) session.getAttribute("user"));
        item.setPhoto(file.getBytes());
        service.update(item.getId(), item);
        return "redirect:/items";
    }

    @GetMapping("/itemDetails/{itemId}")
    public String itemDetails(Model model, @PathVariable("itemId") int id, HttpSession session) {
        findUser(model, session);
        model.addAttribute("item", service.findById(id));
        return "itemDetails";
    }

    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable("itemId") int id) {
        service.delete(id);
        return "redirect:/items";
    }

    @GetMapping("/photoCar/{itemId}")
    public ResponseEntity<Resource> download(@PathVariable("itemId") Integer itemId) {
        Item item = service.findById(itemId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(item.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(item.getPhoto()));
    }

    public void findUser(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
        if (user == null) {
        user = new User();
        user.setName("Гость");
    }
      model.addAttribute("user", user);
    }
}

