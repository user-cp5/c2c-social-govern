package com.zhss.order.service;

import com.zhss.inventory.service.InventoryApi;
import org.springframework.cloud.netflix.feign.FeignClient;



@FeignClient(value = "inventory-service")
public interface InventoryService extends InventoryApi {

}