package org.bilal.debtfree.DAO;

import java.util.List;

public interface daoInterface<E> {

    public boolean addData(E data);
    public int deleteData(E data);
    public int updateData(E data);

    public List<E> showData(int user_id);
}
