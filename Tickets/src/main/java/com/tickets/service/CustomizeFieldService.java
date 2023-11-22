package com.tickets.service;


import com.tickets.Entity.CustomizedFiled;

import java.util.List;

public interface CustomizeFieldService {
    List<CustomizedFiled> getAllCustomizedFields();
    CustomizedFiled getCustomizedFieldById(String id);
    CustomizedFiled createCustomizedField(CustomizedFiled customizedFiled);
    CustomizedFiled updateCustomizedField(String id, CustomizedFiled updatedCustomizedField);
    void deleteCustomizedField(String id);
}

