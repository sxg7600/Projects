package com.company;
import java.util.Scanner;
class Library {
    Scanner books = new Scanner(System.in);
    Scanner books_listing = new Scanner(System.in);
    String []total_books = new String[100];
    String []issued_books = new String[100];
    String []issued_books_copy = new String[100];
    int no_of_books = 0;
    void addBook()
    {
        System.out.print("Please enter the no. of books you want to add: ");
        int add_books = books_listing.nextInt();
        int i;
        System.out.println("Please add the necessary books below: ");
        for(i = no_of_books; i < add_books; i++)
        {
            System.out.print("Book no " + (i + 1) + ": ");
            total_books[i] = books.nextLine();
        }
        this.no_of_books += add_books;
        if (add_books == 1)
        {
            System.out.println(add_books + " book successfully added.");
        }
        else{
            System.out.println(add_books + " books successfully added.");
        }
    }
    int k = 0;
    void addBook(int return_books)
    {
        int i, p = 0, g = 0, rb = 0;
        boolean condition;
        String []not_our_books = new String[100];
        String return_book;
        System.out.println("Please add the necessary books below: ");
        for(i = 0; i < return_books; i++) {
            condition = false;
            System.out.print("Book no " + (i + 1) + ": ");
            return_book = books.nextLine();
            for (p = 0; p < this.k; p++) {
                if(return_book.equals(issued_books_copy[p]))
                {
                    this.total_books[no_of_books] = return_book;
                    no_of_books += 1;
                    rb += 1;
                    condition = true;
                }
            }
            if(!condition) {
                not_our_books[g] = return_book;
                g++;
            }
        }

        if(g <= 1)
        {
            System.out.println("Sorry! Book named " + not_our_books[0] + " was never issued!");
        }
        else{
            System.out.println("Sorry! The following books were never issued!");
            for( int a = 0; a < g; a++)
            {
                System.out.printf("%d ", (a + 1));
                System.out.println(not_our_books[a]);
            }
        }
        if (rb == 1)
        {
            System.out.println(rb + " book successfully returned.");
        }
        else{
            System.out.println(rb + " books successfully returned.");
        }
    }
    void issueBook()
    {
        System.out.print("Please enter the number of books you want: ");
        int issue_book = books_listing.nextInt();
        int i;
        int j;
        boolean condition = false;
        for(i = 0; i < issue_book; i++)
        {
            System.out.print("Book no " + (i + 1) + ": " );
            String issue_books = books.nextLine();
            issued_books[i] = issue_books;
        }
        for(i = 0; i < issue_book; i++)
        {
            for(j = 0; j < no_of_books; j++)
            {
                if(issued_books[i].equals(total_books[j])) {
                    System.out.println(issued_books[i] + " successfully issued.");
                    condition = true;
                    this.issued_books_copy[this.k] = issued_books[i];
                    this.k ++;
                    break;
                }
                else{
                    condition = false;
                }
            }
            if(!condition)
            {
                System.out.println(issued_books[i] + " not available at the moment.");
            }
        }
        int m = 0;
        int p = 0;
        for(i = 0; i < issue_book; i++) {
            for (j = 0; j < no_of_books; j++) {
                if (!(issued_books[i].equals(total_books[j]))) {
                    this.total_books[m] = total_books[j];
                    m++;
                }
            }
            this.no_of_books = m;
            m = 0;
            }
        }
    void returnBook()
    {
        System.out.print("Please enter the number of books you're returning': ");
        int return_book = books_listing.nextInt();
        addBook(return_book);
    }
    void showAvailableBooks()
    {
        int i;
        if(no_of_books == 0)
        {
            System.out.println("Sorry! No books available at the moment.");
        }
        else{
            System.out.println("The available books at the moment are: ");
            for(i = 0; i < no_of_books; i++)
            {
                System.out.print("Book no " + (i+1) + ": ");
                System.out.println(total_books[i]);
            }
        }
    }
}
public class array{
    public static void main(String[] args){
        Library TULibrary = new Library();
        TULibrary.addBook();
        TULibrary.issueBook();
        TULibrary.showAvailableBooks();
        TULibrary.returnBook();
        TULibrary.showAvailableBooks();
    }
}