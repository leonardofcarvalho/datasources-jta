package br.com.datasources;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.datasources.repository.customer1.Customer;
import br.com.datasources.repository.customer1.CustomerRepository;
import br.com.datasources.repository.customer2.CustomerRepository2;

@Controller
@ResponseBody
public class SampleController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerRepository2 customerRepository2;

	@RequestMapping(value = "/addAll", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
//	@Transactional
	public void addAll() {
		customerRepository.deleteAll();
		
		customerRepository.save(new Customer("Jack", "Bauer"));
		System.out.println(customerRepository.findAll().size());
		customerRepository.save(new Customer("Chloe", "O'Brian"));
		System.out.println(customerRepository.findAll().size());
		customerRepository.save(new Customer("Kim", "Bauer"));
		System.out.println(customerRepository.findAll().size());
		customerRepository.save(new Customer("David", "Palmer"));
		System.out.println(customerRepository.findAll().size());
		customerRepository.save(new Customer("Michelle", "Dessler"));
		System.out.println(customerRepository.findAll().size());
	}

	@RequestMapping(value = "/listAll", method = { RequestMethod.POST, RequestMethod.GET })
	public Collection<Customer> listAll() {
		return customerRepository.findAll();
	}

	@RequestMapping(value = "/addAll2")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@Transactional
	public void addAll2() {
		customerRepository2.deleteAll();
		customerRepository.deleteAll();

		System.out.println(customerRepository.findAll().size());
		System.out.println(customerRepository2.findAll().size());

		customerRepository2.save(new br.com.datasources.repository.customer2.Customer("Jack2", "Bauer"));
		customerRepository2.save(new br.com.datasources.repository.customer2.Customer("Chloe2", "O'Brian"));
		customerRepository2.save(new br.com.datasources.repository.customer2.Customer("Kim2", "Bauer"));
		customerRepository2.save(new br.com.datasources.repository.customer2.Customer("David2", "Palmer"));
		customerRepository2.save(new br.com.datasources.repository.customer2.Customer("Michelle2", "Dessler"));

		customerRepository.save(new Customer("Jack", "Bauer"));
		customerRepository.save(new Customer("Chloe", "O'Brian"));
		customerRepository.save(new Customer("Kim", "Bauer"));
		customerRepository.save(new Customer("David", "Palmer"));
		customerRepository.save(new Customer("Michelle", "Dessler"));

		System.out.println(customerRepository2.findAll().size());
		System.out.println(customerRepository.findAll().size());
	}

	@RequestMapping(value = "/listAll2", method = { RequestMethod.POST, RequestMethod.GET })
	public Collection<br.com.datasources.repository.customer2.Customer> listAll2() {
		return customerRepository2.findAll();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleController.class, args);
	}
}