package java8.ex01;

import java8.data.Data;
import java8.data.domain.Order;
import java8.data.domain.Pizza;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 01 - Recherche
 */
public class Stream_01_Test {

    @Test
    public void test_stream_filter() throws Exception {
        List<Pizza> pizzas = new Data().getPizzas();

        // TODO récupérer la liste des pizzas dont le prix est >= 1300
        // TODO utiliser l'API Stream
		List<Pizza> result = pizzas.stream().filter(p->p.getPrice()>1299).collect(Collectors.toList());

        assertThat(result, hasSize(3));
        assertThat(result, everyItem(hasProperty("price", anyOf(equalTo(1300), greaterThan(1300)))));
    }

    @Test
    public void test_stream_anyMatch() throws Exception {

        List<Pizza> pizzas = new Data().getPizzas();

        // TODO valider si au moins une pizza à un prix >= 1300
		List<Pizza> result = pizzas.stream().filter(p->p.getPrice()>1299).collect(Collectors.toList());
		Boolean result1 = null;
		if(result.size() > 0) {
			result1 = true;
		} else {
			result1 = false;
		}

		// TODO valider si au moins une pizza à un prix >= 2000
		List<Pizza> resultbis = pizzas.stream().filter(p->p.getPrice()>1999).collect(Collectors.toList());
		Boolean result2 = null;
		if(resultbis.size() > 0) {
			result2 = true;
		} else {
			result2 = false;
		}

        assertThat(result1, is(true));
        assertThat(result2, is(false));
    }

    @Test
    public void test_stream_allMatch() throws Exception {

        List<Pizza> pizzas = new Data().getPizzas();

        
        // TODO valider que toutes les pizzas ont un prix >= 1300
        Boolean result1 = null;
		List<Pizza> result = pizzas.stream().filter(p->p.getPrice()>1299).collect(Collectors.toList());
		if(result.size() == pizzas.size()) {
			result1 = true;
		} else {
			result1 = false;
		}
        // TODO valider que toutes les pizzas ont un prix >= 900
        Boolean result2 = null;
		List<Pizza> resultbis = pizzas.stream().filter(p->p.getPrice()>899).collect(Collectors.toList());
		if(resultbis.size() == pizzas.size()) {
			result2 = true;
		} else {
			result2 = false;
		}
        assertThat(result1, is(false));
        assertThat(result2, is(true));
    }


    @Test
    public void test_stream_noneMatch() throws Exception {

        List<Pizza> pizzas = new Data().getPizzas();

        // TODO valider qu'aucune pizza n'a un prix >= 2000
        Boolean result1 = null;
		List<Pizza> result = pizzas.stream().filter(p->p.getPrice()>2000).collect(Collectors.toList());
		if(result.size() == 0) {
			result1 = true;
		} else {
			result1 = false;
		}
        assertThat(result1, is(true));
    }

    @Test
    public void test_stream_filter_and_match() throws Exception {

        List<Order> orders = new Data().getOrders();

        // TODO récupérer toutes les commandes dont
        // TODO le prénom du client est "Johnny"
        // TODO dont au moins une pizza a un prix >= 1300		
        List<Order> result = orders.stream().filter(o->o.getCustomer().equals("Johnny"))
//        		.forEach(o -> {
//        			boolean test = false;
//        			for(int i=0 ; i< o.getPizzas().size();i++) {
//        				if(o.getPizzas.get(i).getPrice() > 1299) {
//        					test = true;
//        					break;
//        				}
//        			}
//
//        			assert test = true;
//        		})
        		.collect(Collectors.toList());
        //todo : check from all pizza price
        // p -> {
//    	assert o.getPizzas.getPrice > 1299;
        assertThat(result, hasSize(1));
        assertThat(result.get(0), hasProperty("id", is(8)));
    }

    @Test
    public void test_stream_findFirst() throws Exception {
        List<Order> orders = new Data().getOrders();

        // TODO récupérer une commande faite par un client dont le prénom est "Sophie"
        Optional<Order> result = orders.stream().filter(o->o.getCustomer().equals("Sophie"))
        		.findFirst();

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void test_stream_max() throws Exception {
        List<Pizza> pizzas = new Data().getPizzas();

        // TODO Trouver la pizza la plus chère
        Optional<Pizza> result = pizzas.stream().sorted((pizza1, pizza2)->Integer
        		.compare(
        				pizza2.getPrice(), 
        				pizza1.getPrice())
        		)
        		.findFirst();
        System.out.println(result.get().getPrice());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), hasProperty("id", is(5)));
        assertThat(result.get(), hasProperty("name", is("La Cannibale")));
        assertThat(result.get(), hasProperty("price", is(1550)));
    }
    
    @Test
    public void test_stream_min() throws Exception {
        List<Order> orders = new Data().getOrders();

        List<Pizza> pizzas = new Data().getPizzas();

        // TODO Trouver la pizza la moins chère dont le prix est >= 950
        Optional<Pizza> result = pizzas.stream()
        		.filter(p->p.getPrice()>949)
        		.sorted((pizza1, pizza2)->Integer
        		.compare(
        				pizza1.getPrice(), 
        				pizza2.getPrice())
        		)
        		.findFirst();
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), hasProperty("id", is(3)));
        assertThat(result.get(), hasProperty("name", is("La Reine")));
        assertThat(result.get(), hasProperty("price", is(1000)));
    }
    
    @Test
    public void test_sorted() throws Exception {
        List<Order> orders = new Data().getOrders();

        // TODO Trier les commandes sur le nom de famille du client
        List<Order> result = orders.stream().sorted((order1, order2) 
                -> Character.compare(
                		order1.getCustomer().getFirstname().charAt(order1.getCustomer().getFirstname().length() - 1),
                		order2.getCustomer().getFirstname().charAt(order2.getCustomer().getFirstname().length() - 1)
                		)
                )
        		.collect(Collectors.toList());
        assertThat(result, hasSize(8));
        
        // Résultat: les 4 premières commandes de la liste appartiennent à M. Cotillard
        assertThat(result.get(0).getCustomer(), hasProperty("id", is(2)));
        assertThat(result.get(1).getCustomer(), hasProperty("id", is(2)));
        assertThat(result.get(2).getCustomer(), hasProperty("id", is(2)));
        assertThat(result.get(3).getCustomer(), hasProperty("id", is(2)));
        
       // Résultat: les 4 premières commandes de la liste appartiennent à J. Halliday
        assertThat(result.get(4).getCustomer(), hasProperty("id", is(1)));
        assertThat(result.get(5).getCustomer(), hasProperty("id", is(1)));
        assertThat(result.get(6).getCustomer(), hasProperty("id", is(1)));
        assertThat(result.get(7).getCustomer(), hasProperty("id", is(1)));
        
    }
}
