package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Item;
import ru.job4j.store.ItemStore;

import java.util.Collection;
import java.util.List;

@Service
public class ItemService {
    private final ItemStore store;

    public ItemService(ItemStore store) {
        this.store = store;
    }

    public Collection<Item> findAll() {
        return store.findAll();
    }

    public Item add(Item item) {
        return store.add(item);
    }

    public void update(int id, Item item) {
        store.update(id, item);
    }

    public Item findById(int id) {
        return store.findById(id);
    }

    public List<Item> findByBrand(int brandId) {
        return store.findByBrand(brandId);
    }

    public List<Item> findByBody(int bodyId) {
        return store.findByBody(bodyId);
    }

    public List<Item> findByCategory(int categoryId) {
        return store.findByCategory(categoryId);
    }

    public void  delete(int id) {
        store.delete(id);
    }
}
