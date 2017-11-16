package system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import system.model.Customer;
import system.model.Order;
import system.model.Product;

import java.util.List;

@Repository
@Transactional
@EnableTransactionManagement
public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    @Transactional
    public void addOrder(Order order) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(order);
        logger.info("Order saved successfully, Order Details= "+order);
    }

    @Override
    @Transactional
    public void updateOrder(Order order) {
        Session session = this.sessionFactory.getCurrentSession();
                
        session.update(order);
        logger.info("Order updated successfully, Order Details= "+ order);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Order> listOrders() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Order> ordersList = session.createQuery("FROM Orders").list();
        for(Order o : ordersList){
            logger.info("Order List:"+ o);
        }
        return ordersList;
    }

    @Override
    @Transactional
    public Order getOrderById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Order order = (Order) session.load(Order.class, new Integer(id));
        logger.info("Order loaded successfully, Order details= "+ order);
        return order;
    }

    @Override
    @Transactional
    public List<Order> getOrderByCustomer(Customer customer){
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Orders o" +
                " INNER JOIN Customers c ON o.CUSTOMER_ID = c.id" +
                " INNER JOIN Products p on o.PRODUCT_ID = p.id" +
                " WHERE c." + customer.getId() + "";
        List<Order> orderList = session.createQuery(hql).list();
        for(Order o: orderList){
            logger.info("Order List:" + o);
        }
        return orderList;
    }

    @Override
    @Transactional
    public List<Order> getOrderByProduct (Product product){
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Orders o" +
                " INNER JOIN Customers c ON o.CUSTOMER_ID = c.id" +
                " INNER JOIN Products p on o.PRODUCT_ID = p.id" +
                " WHERE c." + product.getId() + " ";
        List<Order> orderList = session.createQuery(hql).list();
        for(Order o: orderList){
            logger.info("Order List:" + o);
        }
        return orderList;
    }

    @Override
    @Transactional
    public void removeOrder(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Order order = (Order) session.load(Order.class, new Integer(id));
        if(null != order){
            session.delete(order);
        }
        logger.info("Order deleted successfully, order details= "+ order);
    }

}
