UPDATE tblSubscriptionInfo
SET customer_contact_phone = '+6591234567'
WHERE customer_id = 123456;

/* How to speed up UPDATE query
1. Index customer_id column.
    Indexes allow the database management system to quickly locate the row and execute the update statement.
    This is in contrast to when the column is not indexed, which causes a full scan of the table to be required (which can be costly when this table is huge).
2. Partitioning on customer_id.
    customer_id is of int type and lends itself to be partionable into ranges, for instance
    This improves query performance as it significantly reduces the amount of data that needs to be scanned when executing the above statement.
3. Connection Pooling
    Reuse database connections to ensure that we do not establish new ones for every single update request, which can generate significant overheads and slow
    the update statement.
*/

-- Q1 Number of subscribers whose subscriptions will be ending in 2023;
-- Assume that each customer_id can have multiple subscription_id to them

SELECT
    COUNT(DISTINCT customer_id) AS subscribers_ending_2023
FROM
    tblSubscriptionInfo
WHERE
    YEAR(subscription_end_date) = 2023;

-- Q2 Number of subscribers who have subscribed for more than 3 months in 2022;
SELECT
    COUNT(DISTINCT customer_id) AS subscribers_3m_2022
FROM
    tblSubscriptionInfo
WHERE
    (YEAR(subscription_start_date) = 2022 AND PERIOD_DIFF(date_format(DATE'2022-12-31', '%Y%m'), date_format(subscription_start_date, '%Y%m')) > 3) OR
    (YEAR(subscription_start_date) = 2021 AND PERIOD_DIFF(date_format(subscription_end_date, '%Y%m'), date_format(DATE'2022-01-01', '%Y%m')) > 3);

-- Q3 Subscribers who have subscribed for more than two products;
SELECT DISTINCT
    customer_id
FROM
    tblSubscriptionInfo
GROUP BY
    customer_id
HAVING
    COUNT(DISTINCT product_id) > 2;

-- Q4 Product with the most/2ndmost/3rdmost number of subscribers in 2022
-- For this question, I will define "in 2022" as any subscriptions that are active for at least a day in 2022

WITH temp AS (
    SELECT
        product_id
         ,COUNT(DISTINCT customer_id) AS subscriber_cnt
    FROM
        tblSubscriptionInfo
    WHERE
        YEAR(subscription_start_date) = 2022 OR YEAR(subscription_end_date) = 2022
    GROUP BY
        1
), temp_ranked AS (
SELECT
    product_id
    , RANK() OVER (ORDER BY subscriber_cnt DESC) AS subs_rank
FROM
    temp
)

SELECT
    product_id
     ,subs_rank
FROM
    temp_ranked
WHERE
    subs_rank <= 3;

-- Q5 Number of subscribers who have re-subscribed more than once for each product;

WITH temp AS (
    SELECT
        product_id
        ,customer_id
    FROM
        tblSubscriptionInfo
    GROUP BY
        1,2
    HAVING
        COUNT(subscription_id) > 1
)

SELECT
    product_id
    ,COUNT(customer_id) AS resubscribed_cnt
FROM
    temp
GROUP BY
    1;

-- Q6 Subscribers who have re-subscribed a higher version of the product in 2023 - for example Autocad 2022 to Autocad 2023.

-- There is insufficient information to distinguish product versions in the given schema
-- We can only make some assumptions on what information/logic would give us the required query
-- Here, I assume that product names are same cross all versions; and for each product name, a higher product_id indicates a higher version

SELECT DISTINCT
    customer_id
FROM
    tblSubscriptionInfo org JOIN
    tblSubscriptionInfo resub ON
    org.customer_id = resub.customer_id AND org.product_name = resub.product_name
WHERE
    resub.product_id > org.product_id AND
    YEAR(resub.subscription_start_date) = 2023
