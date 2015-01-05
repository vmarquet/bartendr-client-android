package com.github.vmarquet.bartendr;


import java.util.ArrayList;


// singleton pattern, to store the articles the user want to order
public class Order {

    private ArrayList<Article> order;

    // link to the singleton's instance
    private static Order INSTANCE = null;

    // private constructor
    private Order() {
        order = new ArrayList<Article>();
    }

    // to get the instance
    public static synchronized Order getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Order();
        return INSTANCE;
    }

    // getters:

    // to get the list size
    public int getNumberOfArticles() {
        return this.order.size();
    }

    // to get the article at a given position
    public Article getArticleAt(int i) {
        return this.order.get(i);
    }

    // setters:

    // to add an article to the order list
    public void add(Article article) {
        this.order.add(article);
    }

    // to clear the list
    public void clear() {
        this.order.clear();
    }

}
