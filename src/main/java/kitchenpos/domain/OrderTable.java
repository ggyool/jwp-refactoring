package kitchenpos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_group_id", foreignKey = @ForeignKey(name = "fk_order_table_to_table_group"))
    private TableGroup tableGroup;

    @Column
    private int numberOfGuests;

    @Column
    private boolean empty;

    protected OrderTable() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public TableGroup getTableGroup() {
        return tableGroup;
    }

    public void setTableGroup(TableGroup tableGroup) {
        this.tableGroup = tableGroup;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(final int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(final boolean empty) {
        this.empty = empty;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private TableGroup tableGroup;
        private int numberOfGuests;
        private boolean empty;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder tableGroup(TableGroup tableGroup) {
            this.tableGroup = tableGroup;
            return this;
        }

        public Builder numberOfGuests(int numberOfGuests) {
            this.numberOfGuests = numberOfGuests;
            return this;
        }

        public Builder empty(boolean empty) {
            this.empty = empty;
            return this;
        }

        public OrderTable build() {
            OrderTable orderTable = new OrderTable();
            orderTable.setId(id);
            orderTable.setTableGroup(tableGroup);
            orderTable.setNumberOfGuests(numberOfGuests);
            orderTable.setEmpty(empty);
            return orderTable;
        }
    }
}
