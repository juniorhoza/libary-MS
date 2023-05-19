package com.example.libmswgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.sql.*;
import java.util.UUID;

public class HelloController {
    @FXML
    private TextField title;

    private String titlevar;
    @FXML
    private TextField author;
    private String authorvar;
    @FXML
    private TextField copies;
    private int copiesvar;
    @FXML
    Label titleErr;
    @FXML
    Label authorErr;
    @FXML
    Label copiesErr;
    @FXML
    TableView  booksTable;
    @FXML
    TableColumn<book,Long> IdCol;
    @FXML
    TableColumn<book,String> titleCol;

    @FXML
    TableColumn<book,String> authorCol;
    @FXML
    TableColumn<book,Integer> copiesCol;
    @FXML
    Button addNew;
    @FXML
    Button update;
    @FXML
    Button loadTable;
    public static Connection con=HelloApplication.connect();

    private book selBook;
    public void showData(){
        booksTable.setRowFactory(tv -> {
            TableRow<book> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Edit");
            editItem.setOnAction(event -> {
                // handle edit action here
                addNew.setVisible(false);
                update.setVisible(true);
                selBook = (book) booksTable.getSelectionModel().getSelectedItem();
                // retrieve relevant data from selectedBook object
                title.setText(selBook.getTitle());
                author.setText(selBook.getAuthor());
                copies.setText(Integer.toString(selBook.getNum_of_copies()));

            });
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                book selectedBook = (book) booksTable.getSelectionModel().getSelectedItem();
                // retrieve relevant data from selectedBook object
                String id = selectedBook.getId();
                System.out.println(selectedBook.toString());
                try{
                    String sql= "Delete from books where book_id = ?";
                    PreparedStatement ps=con.prepareStatement(sql);
                    ps.setString(1,id);
                    ps.executeUpdate();
                    showData();
                }catch(Exception e){
                    e.printStackTrace();
                }



                // perform deletion operation using the retrieved data
                // handle delete action here
            });
            contextMenu.getItems().addAll(editItem, deleteItem);
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
            });
            return row;
        });

        ObservableList<book> data = FXCollections.observableArrayList();
        IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        copiesCol.setCellValueFactory(new PropertyValueFactory<>("num_of_copies"));
        try{
            Statement stm= con.createStatement( );
            ResultSet rs=stm.executeQuery("select * from books");
            while (rs.next()){
                UUID id= UUID.fromString(rs.getString(1));
                String titles= rs.getString(2);
                String authors= rs.getString(3);
                int copies= rs.getInt(4);
                data.add(new book(id,titles,authors,copies));
            }
            booksTable.setItems(data);


        }catch(Exception e){
                e.printStackTrace();
        }


    }

    public void AddnewBook(){
        if ((titlevar=title.getText()).equals("")==true) {
            titleErr.setVisible(true);
        }else titleErr.setVisible(false);
        if ((authorvar=author.getText()).equals("")==true) {
            authorErr.setVisible(true);
        }else authorErr.setVisible(false);

        try{
            copiesvar = Integer.parseInt(copies.getText());
            if(copiesvar<0){
                copiesErr.setVisible(true);
            }
            if((copiesErr.isVisible())==true && copiesvar>=0){
                copiesErr.setVisible(false);

            }
        }catch (Exception e){
            copiesErr.setVisible(true);
        }
        if(titlevar!=""&&copiesvar>0 &&authorvar!=" "){
            try{
                book temp= new book(titlevar,authorvar,copiesvar);
                PreparedStatement sel=con.prepareStatement("select count(title) from books where title like CONCAT('%', ? ,'%') and author = ?");
                 sel.setString(1,temp.getTitle());
                 sel.setString(2, temp.getAuthor());
                 ResultSet res= sel.executeQuery();
                String query= "insert into books(book_id,title,author,copies)values(?,?,?,?)";
                if(res.next()){
                    if(res.getInt(1)==0){
                        PreparedStatement ps= con.prepareStatement(query);
                        ps.setString(1,temp.getId());
                        ps.setString(2,temp.getTitle());
                        ps.setString(3,temp.getAuthor());
                        ps.setInt(4,temp.getNum_of_copies());

                        ps.executeUpdate();
                        title.setText("");
                        author.setText("");
                        copies.setText("");

                        showData();

                    }else{
                        System.out.println("book exists already " + res.getInt(1));
                    }
                }



            }catch (Exception e){
                e.printStackTrace();
            }
        }



    }
    public void loadtable(){
        showData();
        addNew.setVisible(true);
        loadTable.setVisible(false);
    }
    public void deleteItem(){

    }
    public void updateBook(){
        if ((titlevar=title.getText()).equals("")==true) {
            titleErr.setVisible(true);
        }else titleErr.setVisible(false);
        if ((authorvar=author.getText()).equals("")==true) {
            authorErr.setVisible(true);
        }else authorErr.setVisible(false);

        try{
            copiesvar = Integer.parseInt(copies.getText());
            if(copiesvar<0){
                copiesErr.setVisible(true);
            }
            if((copiesErr.isVisible())==true && copiesvar>=0){
                copiesErr.setVisible(false);

            }
        }catch (Exception e){
            copiesErr.setVisible(true);
        }
        if(titlevar!=""&&copiesvar>0 &&authorvar!=" "){
            try{
                PreparedStatement updateQuery = con.prepareStatement("Update  books set title=? , author=? ,copies=? where book_id=?");
                updateQuery.setString(1,titlevar);
                updateQuery.setString(2,authorvar);
                updateQuery.setInt(3,copiesvar);
                updateQuery.setString(4, selBook.getId());
                updateQuery.executeUpdate();
                title.setText("");
                author.setText("");
                copies.setText("");
                showData();
                addNew.setVisible(true);
                update.setVisible(false);


            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
}