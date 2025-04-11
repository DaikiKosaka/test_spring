package jp.co.sss.test_spring.status;

public enum OrderStatus {
    PENDING,      // 注文待機中
    COMPLETED,    // 注文完了
    CANCELLED,    // 注文キャンセル
    SHIPPED       // 注文発送済み
}
