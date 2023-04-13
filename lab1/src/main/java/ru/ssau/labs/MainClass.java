package ru.ssau.labs;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("\n===POSTGRES JOIN===\n");
        PostgresQueries.join();
        System.out.println("===================\n");
        System.out.println("===POSTGRES HAVING===\n");
        PostgresQueries.having();
        System.out.println("===================\n");
        System.out.println("===POSTGRES RECURSIVE===\n");
        PostgresQueries.recursive();
        System.out.println("===================\n");
        System.out.println("===CLICKHOUSE LIMIT===\n");
        ClickHouseQueries.limitQuery();
        System.out.println("===================\n");
        System.out.println("===CLICKHOUSE REPLACING MERGE TREE===\n");
        ClickHouseQueries.replacingMergeTree();
        System.out.println("===================\n");
        System.out.println("===COLLAPSING REPLACING MERGE TREE===\n");
        ClickHouseQueries.collapsingMergeTree();
        System.out.println("===================");
    }
}
