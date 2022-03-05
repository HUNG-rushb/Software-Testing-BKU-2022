package user.search;

import user.Client;

import java.util.List;

public interface Search {
    void listAllBooks(Client...client);
    void selectBookAfterList(int Size, List<Integer>list, Client...client);
    void searchByTitle(Client...client);
    void searchByAuthor(Client...client);
    void searchByCategory(Client...client);
}
