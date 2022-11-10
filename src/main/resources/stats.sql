Select sum(user_Count) as user_Count, sum(post_Count) as post_Count, sum(comment_Count) as comment_Count, sum(like_Count) as like_Count from (
Select
Count(*) as user_Count,
0 as post_Count,
0 as comment_Count,
0 as Like_Count
From "user"
UNION ALL
Select
0,
Count(*),
0,
0
From post
UNION ALL
Select
0,
0,
Count(*),
0
From comment
UNION ALL
Select
0,
0,
0,
Count(*)
From "like") as RawData;