package com.n2o.tombile.core.common.service;

import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.util.Util;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

import static com.n2o.tombile.core.common.util.Constants.ERROR_ITEM_NOT_FOUND;

public interface CRUDService<E, ID, I extends JpaRepository<E, ID>> {
    I getRepository();

    Class<E> getEntity();
    Class<?> getPersistRSP();
    Class<?> getListItem();
    Class<?> getDetails();

    default List<Object> getAll() {
        try {
            List<E> items = getRepository().findAll();
            List<Object> listItems = new ArrayList<>();
            items.forEach(item -> listItems.add(Util.cloneObject(item, getListItem())));
            return listItems;
        } catch (Exception e) {
            throw e;
        }
    }

    default Object getItemById(ID id) {
        try {
            E item = getRepository().findById(id)
                    .orElseThrow(() -> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
            return Util.cloneObject(item, getDetails());
        } catch (Exception e) {
            throw e;
        }
    }

    default Object addItem(Object request) {
        try {
            E item = Util.cloneObject(request, getEntity());
            item = getRepository().save(item);
            return Util.cloneObject(item, getPersistRSP());
        } catch (Exception e) {
            throw e;
        }
    }

    default Object editItem(Object request, ID id) {
        try {
            E item = getRepository().findById(id)
                    .orElseThrow(() -> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
            Util.copyProperties(request, item);
            item = getRepository().save(item);
            return Util.cloneObject(item, getPersistRSP());
        } catch (Exception e) {
            throw e;
        }
    }

    default void deleteItem(ID id) {
        getRepository().findById(id).ifPresent(getRepository()::delete);
    }
}
