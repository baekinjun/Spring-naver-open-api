package com.example.restaurants.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract public class MemoryDbRepositoryAbstract<T extends MemoryDbEntity> implements MemoryDbRepositoryIfs<T> {

    private final List<T> db = new ArrayList<>();
    private int index = 0;

    @Override
    public Optional<T> findById(int index) {
        return db.stream().filter(it -> it.getIndex() == index).findFirst();

    }

    @Override
    public T save(T entity) {

        var OptionalEntity = db.stream().filter(it -> it.getIndex() == entity.getIndex()).findFirst();
        //db 에 이미 데이터가 없는경우
        if (OptionalEntity.isEmpty()) {
            index++;
            entity.setIndex(index);
            db.add(entity);
            return entity;

        } else {
            //db 에 이미 데이터가 있는경우
            var preIndex = OptionalEntity.get().getIndex();
            entity.setIndex(preIndex);

            deleteById(preIndex);
            db.add(entity);
            return entity;

        }



    }

    @Override
    public void deleteById(int index) {
        var optionalEntity = db.stream().filter(it -> it.getIndex() == index).findFirst();
        if (optionalEntity.isPresent()) {
            db.remove(optionalEntity.get());
        }

    }

    @Override
    public List<T> listAll() {
        return db;
    }
}