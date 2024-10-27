import java.util.ArrayList;
import java.util.Scanner;

// Book class
class Book {
    private String book_title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String book_title, String author, String isbn) {
        this.title = book_title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Available: " + isAvailable;
    }
}

// Library class
class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void displayAvailableBooks() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book);
            }
        }
    }

    public Book borrowBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && book.isAvailable()) {
                book.setAvailable(false);
                return book;
            }
        }
        return null; // Book not found or not available
    }

    public void returnBook(Book book) {
        book.setAvailable(true);
    }
}

// User class
class User {
    private static int userIdCounter = 1;
    private int userId;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public User() {
        this.userId = userIdCounter++;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}

// FictionBook class (inherits from Book)
class FictionBook extends Book {
    private String genre;

    public FictionBook(String title, String author, String isbn, String genre) {
        super(title, author, isbn);
        this.genre = genre;
    }

    public String toString() {
        return super.toString() + ", Genre: " + genre;
    }
}

// NonFictionBook class (inherits from Book)
class NonFictionBook extends Book {
    private String subject;

    public NonFictionBook(String title, String author, String isbn, String subject) {
        super(title, author, isbn);
        this.subject = subject;
    }

    public String toString() {
        return super.toString() + ", Subject: " + subject;
    }
}

// Main class
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Adding sample books to the library(FictionBook,Non FictionBook)
        library.addBook(new FictionBook("kali yugaya", "Martin Wickramasinghe", "011111", "Novels"));
        library.addBook(new NonFictionBook("Sapiens", "Yuval Noah Harari", "011112", "History"));
		library.addBook(new NonFictionBook("My Name Is Barbra", "Barbra Streisand", "011113", "History"));
        library.addBook(new FictionBook("To Kill a Mockingbird", "Harper Lee", "011114", "Classics"));

        // Creating a sample user
        User user = new User();

        // Menu
        int choice;
        do {
			System.out.println("");
			System.out.println("                                                     ********************************************************************");
			System.out.println("                                                                            Welcome Online Library");
			System.out.println("                                                     ********************************************************************");
            System.out.println("\n1. Display Available Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
			System.out.println("4. Add a New Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.displayAvailableBooks();
                    break;
                case 2:
                    System.out.print("Enter ISBN of the book you want to borrow: ");
                    scanner.nextLine(); // Consume the newline character
                    String isbnToBorrow = scanner.nextLine();
                    Book borrowedBook = library.borrowBook(isbnToBorrow);
                    if (borrowedBook != null) {
                        user.borrowBook(borrowedBook);
                        System.out.println("Book borrowed successfully.");
                    } else {
                        System.out.println("Book not found or not available for borrowing.");
                    }
                    break;
                case 3:
                    if (user.getBorrowedBooks().isEmpty()) {
                        System.out.println("You haven't borrowed any books.");
                    } else {
                        System.out.println("Your Borrowed Books:");
                        for (Book userBook : user.getBorrowedBooks()) {
                            System.out.println(userBook);
                        }
                        System.out.print("Enter ISBN of the book you want to return: ");
                        scanner.nextLine(); // Consume the newline character
                        String isbnToReturn = scanner.nextLine();
                        Book returnedBook = null;
                        for (Book userBook : user.getBorrowedBooks()) {
                            if (userBook.getIsbn().equals(isbnToReturn)) {
                                returnedBook = userBook;
                                break;
                            }
                        }
                        if (returnedBook != null) {
                            user.returnBook(returnedBook);
                            library.returnBook(returnedBook);
                            System.out.println("Book returned successfully.");
                        } else {
                            System.out.println("Book not found in your borrowed books.");
                        }
                    }
                    break;
				case 4:
                    // Adding a new book to the library
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter the title of the new book: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter the author of the new book: ");
                    String newAuthor = scanner.nextLine();
                    System.out.print("Enter the ISBN of the new book: ");
                    String newIsbn = scanner.nextLine();
                    System.out.print("Is it a Fiction or Non-Fiction book? Enter 'Fiction' or 'NonFiction': ");
                    String bookType = scanner.nextLine();
                    Book newBook;
                    if (bookType.equalsIgnoreCase("Fiction")) {
                        System.out.print("Enter the genre of the new fiction book: ");
                        String newGenre = scanner.nextLine();
                        newBook = new FictionBook(newTitle, newAuthor, newIsbn, newGenre);
                    } else {
                        System.out.print("Enter the subject of the new non-fiction book: ");
                        String newSubject = scanner.nextLine();
                        newBook = new NonFictionBook(newTitle, newAuthor, newIsbn, newSubject);
                    }
                    library.addBook(newBook);
                    System.out.println("New book added successfully.");
                    break;
                case 5:
                    System.out.println("Exiting the Library Management System. Goodbye!");
					System.out.println("");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 5);
    }
}