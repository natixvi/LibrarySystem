import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Library extends JFrame implements Runnable{
    private JMenuBar menuBar;
    private JMenu Menu;
    private JMenuItem iadd, iclose;
    private JButton addButton, deleteButton, borrowButton, returnButton, searchButton, showAllBooksButton;
    private CloseAction close_action = new CloseAction();
    private AddAction add_action = new AddAction();
    private BorrowAction borrow_action = new BorrowAction();
    private ReturnAction return_action = new ReturnAction();
    private DeleteAction delete_action = new DeleteAction();
    private SearchAction search_action = new SearchAction();
    private ShowAllBooksAction showAllBooks_action = new ShowAllBooksAction();
    private Map<Integer, Book> books = new TreeMap<>();
    private DefaultListModel listmodel;
    private JList list;
    private JScrollPane  sp;
    private JPanel panel, panel2;
    private static String title2, author2, borrower, booknr, what_factor ;


    public Library(String title){
        super(title);
        addWindowListener(new WindowClosingAdapter());
        setLocationByPlatform(true);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        Menu = new JMenu("Menu");
        iadd = new JMenuItem(add_action);
        iclose = new JMenuItem(close_action);
        menuBar.add(Menu);
        Menu.add(iadd);
        Menu.add(iclose);

        create_book_to_map();

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(2, 1));

        panel2 = new JPanel(new BorderLayout());
        listmodel = new DefaultListModel();
        show_books();
        list = new JList(listmodel);
        sp = new JScrollPane(list);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel2.add(sp);
        pane.add(panel2);

        panel = new JPanel();
        addButton = new JButton(add_action);
        panel.add(addButton);
        deleteButton = new JButton(delete_action);
        panel.add(deleteButton);
        borrowButton = new JButton(borrow_action);
        panel.add(borrowButton);
        returnButton = new JButton(return_action);
        panel.add(returnButton);
        searchButton = new JButton(search_action);
        panel.add(searchButton);
        showAllBooksButton = new JButton(showAllBooks_action);
        panel.add(showAllBooksButton);
        pane.add(panel, BorderLayout.CENTER);

        add(panel);
        setSize(1000,600);

    }

    public void create_book_to_map(){
        books.put(1,new Book("Lolita","Vladimir Nabokov"));
        books.put(2,new Book("Crime and Punishment","Fyodor Dostoevsky"));
        books.put(3,new Book("The Noonday Demon","Andrew Solomon"));
        books.put(4,new Book("Great Gatsby","F.Scott Fitzgerald"));
        books.put(5,new Book("1984","George Orwell"));
        books.put(6,new Book("The Metamorphosis","Franz Kafka"));
        books.put(7,new Book("Demian","Hermann Hesse"));
        books.put(8,new Book("Nature","Ralph Waldo Emerson"));
        books.put(9,new Book("Stories of Your Life and Others","Ted Chiang"));
        books.put(10,new Book("Kafka On The Shore","Haruki Murakami"));
        books.put(11,new Book("Me Before You","Jojo Moyes"));
        books.put(12,new Book("Human Acts","Han Kang"));
        books.put(13,new Book("The Vegetarian","Han Kang"));
        books.put(14,new Book("The White Book","Han Kang"));
    }

    public void show_books(){
        listmodel.removeAllElements();
        listmodel.addElement("List of books :");
        listmodel.addElement("ID                      Author                    Title                 Borrowed");
        Set<Entry<Integer, Book>> entrySet = books.entrySet();
        for(Entry<Integer, Book> b:entrySet){
            listmodel.addElement(b.getKey() + " :    "+ b.getValue());
        }
    }

    @Override
    public void run() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private Library getFrame() {
        return this;
    }

    class WindowClosingAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            if(JOptionPane.showOptionDialog(e.getWindow(),"Do you want to close app ?","Confirmation",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[] {"Yes","No"},"No")==0)
                System.exit(0);
        }
    }

    class CloseAction extends AbstractAction{
        public CloseAction() {
            putValue(Action.NAME, "Close");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Z"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Library kf = getFrame();
            kf.dispatchEvent(new WindowEvent(kf, WindowEvent.WINDOW_CLOSING));
        }
    }

    class AddAction extends AbstractAction{

        public AddAction() {
            putValue(Action.NAME, "Add Book");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            title2 = JOptionPane.showInputDialog("Enter a book's title");
            author2 = JOptionPane.showInputDialog("Enter a book's author");
            try{
                if(title2== null || author2 == null)
                    throw new Exception();

                int id = books.size() + 1;
                books.put(id, new Book(title2, author2));
                show_books();
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(getFrame(), "You enter wrong title or author!","Wrong data", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    class DeleteAction extends AbstractAction{

        public DeleteAction() {
            putValue(Action.NAME, "Delete Book");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            booknr = JOptionPane.showInputDialog("Enter a book's number that you want to delete, it can be a book that is borrowed already!");
            try{
                int digitnumer = Integer.parseInt(booknr);
                if( digitnumer == 0 || digitnumer > books.size()){
                    throw new Exception();
                }
                Book book = books.get(digitnumer);
                if(book.getBorrowed()){
                    throw new Exception();
                }

                books.remove(digitnumer);
                show_books();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(getFrame(),"You enter the wrong number! Please enter the number from book's list that isn't borrowed", "Wrong data!",JOptionPane.INFORMATION_MESSAGE );
            }
        }
    }


    class BorrowAction extends AbstractAction{

        public BorrowAction() {
            putValue(Action.NAME, "Borrow Book");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            booknr = JOptionPane.showInputDialog("Enter a book's number that you want to borrow");
            borrower = JOptionPane.showInputDialog("Enter your name!");
            try{
                int digitnumer = Integer.parseInt(booknr);
                if( digitnumer == 0 || digitnumer > books.size()){
                    throw new Exception();
                }
                if (borrower == null || borrower.length() < 2){
                    throw new Exception();
                }

                Book book = books.get(digitnumer);
                if (book.getBorrowed()){
                    throw new Exception();
                }
                book.setBorrower(borrower);
                book.setBorrowed(Boolean.TRUE);
                show_books();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(getFrame(),"You enter the wrong number or name! Please enter the number from book's list that isn't borrowed or correct name.", "Wrong data!",JOptionPane.INFORMATION_MESSAGE );
            }
        }
    }

    class ReturnAction extends AbstractAction{

        public ReturnAction() {
            putValue(Action.NAME, "Return Book");
        }
        @Override
        public void actionPerformed(ActionEvent e) {

            booknr = JOptionPane.showInputDialog("Enter a book's number that you want to return");
            borrower = JOptionPane.showInputDialog("Enter your name that you enter while you borrowed book!");
            try{
                int digitnumer = Integer.parseInt(booknr);
                if( digitnumer == 0 || digitnumer > books.size()){
                    throw new Exception();
                }
                Book book = books.get(digitnumer);
                if(!book.getBorrowed()){
                    throw new Exception();
                }
                if (borrower == null || !borrower.equals(book.getBorrower())){
                    throw new Exception();
                }

                book.setBorrower("");
                book.setBorrowed(Boolean.FALSE);
                show_books();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(getFrame(),"You enter the wrong number or name! Please enter the number book that you borrowed or correct name you enter while you borrowed book.", "Wrong data!",JOptionPane.INFORMATION_MESSAGE );
            }
        }
    }

    class SearchAction extends AbstractAction{

        public SearchAction() {
            putValue(Action.NAME, "Search Book");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] options = {"author", "title"};
            int x =  JOptionPane.showOptionDialog(getFrame(),"Choose by what factor do you want to search a book","Choose factor", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.DEFAULT_OPTION,null, options, options[0]);
            what_factor = options[x];
            try{
                if(what_factor == null || (!what_factor.equals("author") && !what_factor.equals("title"))){
                    throw new Exception();
                }

                if(what_factor.equals("author")){
                    author2 = JOptionPane.showInputDialog("Enter a book's author that you can search");
                    listmodel.removeAllElements();
                    books.entrySet().stream().filter(map -> map.getValue().getAuthor().equals(author2)).forEach(listmodel::addElement);
                }
                if(what_factor.equals("title")){
                    title2 = JOptionPane.showInputDialog("Enter a book's title that you can search");
                    listmodel.removeAllElements();
                    books.entrySet().stream().filter(m -> m.getValue().getTitle().equals(title2)).forEach(listmodel::addElement);
                }
                if(title2 == null && author2 == null)
                    throw new Exception();
                }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(getFrame(), "You enter wrong title or author!","Wrong data", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    class ShowAllBooksAction extends AbstractAction{
        public ShowAllBooksAction() {
            putValue(Action.NAME, "Show All Books");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            show_books();
        }
    }

}
