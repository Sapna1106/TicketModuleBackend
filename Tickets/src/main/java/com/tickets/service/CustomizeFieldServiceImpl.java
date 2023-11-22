package com.tickets.service;
import com.tickets.Entity.CustomizedFiled;
import com.tickets.Repository.CustomizedFiledRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomizeFieldServiceImpl implements CustomizeFieldService{






        @Autowired
        private CustomizedFiledRepo customizedFiledRepository;

        @Override
        public List<CustomizedFiled> getAllCustomizedFields() {
            return customizedFiledRepository.findAll();
        }

        @Override
        public CustomizedFiled getCustomizedFieldById(String id) {
            return customizedFiledRepository.findById(id).orElse(null);
        }

        @Override
        public CustomizedFiled createCustomizedField(CustomizedFiled customizedFiled) {
            return customizedFiledRepository.save(customizedFiled);
        }

        @Override
        public CustomizedFiled updateCustomizedField(String id, CustomizedFiled updatedCustomizedField) {
            Optional<CustomizedFiled> existingField = customizedFiledRepository.findById(id);

            if (existingField.isPresent()) {
                updatedCustomizedField.setId(existingField.get().getId());
                return customizedFiledRepository.save(updatedCustomizedField);
            }

            return null;
        }

        @Override
        public void deleteCustomizedField(String id) {
            customizedFiledRepository.deleteById(id);
        }
    }


