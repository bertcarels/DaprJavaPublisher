package com.kaboutersoft.group.gtsciais.demo;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.kaboutersoft.group.gtsciais.demo.model.Customer;
import com.kaboutersoft.group.gtsciais.demo.repository.CustomerRepository;
import com.kaboutersoft.group.gtsciais.demo.service.publish.CustomerServicePubSub;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerServicePubSub customerServicePubSub;

 
   
   // @Test
   // public void appHasAGreeting() {
   //     DemoApplication classUnderTest = new DemoApplication();
   //     assertNotNull("app should have a greeting", classUnderTest.getGreeting());
   //  }

  // @Test 
  // public void appHasAGreeting2() {
  //      DemoApplication classUnderTest = new DemoApplication();
  //      assertNotNull("app should have a greeting", null);
  //   }


   @Autowired
   private ApplicationContext applicationContext;

   // Test method to verify ApplicationContext
   @Test
   public void testApplicationContext() {
       assertNotNull(applicationContext);
       // Perform additional checks on the ApplicationContext if needed
   }


    @Test
    public void testGetAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer( "John", "Doe", "john@example.com"));
        customers.add(new Customer( "Jane", "Smith", "jane@example.com"));
        
        when(customerRepository.findAll()).thenReturn(customers);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(customers.size()));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer( "John", "Doe", "john@example.com");
        
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerServicePubSub.publishCustomerEvent(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .content("{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john@example.com\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@example.com"));
    }

    //    getAllCustomers()
    //    List<Customer> findAll();

    //    getCustomerById(Long id)
    //    Optional<Customer> findById(Long id);

    //    createCustomer(Customer customer)
    //    Customer save(Customer customer);

    //    updateCustomer(Long id, Customer updatedCustomer)
    //    Customer save(Customer customer);

    //    deleteCustomer(Long id)
    //    void deleteById(Long id);

    


}
