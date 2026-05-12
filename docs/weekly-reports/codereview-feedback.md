# Instructor Code Review Feedback

**Contact**: Dr. Yiji Zhang (yiji.zhang@northwestern.edu)

**Purpose of This Document**:
The instructor will perform code review with respect to software design, error handling, format and style on the main branch every week starting Week 6 using the letter grade A standards.
The following chapters of the textbook are considered: Chapter 1, 2, 3, 4, 5, 6, 7, 9, and 10. The corresponding lectures are considered, too.

Please note that this feedback does not include evaluation of your progress, the proper use of linters, the quality of your test cases, or your compliance of TDD/BDD workflow.  
You can find the weekly feedback from your dedicated PM/TA for that.

## Week 6 Code Review
I have read every line of production code currently in the main branch. 
Overall, good job! Two small things:
1. In `GameController`, the comments "//model", "//view", "//constructor" should be removed as they don't explain the code.
2. In `Player`, the `public String chooseCard(int cardIndex)` method has hardcoded strings. Are they meant to be temporary strings? If not, you want to create constant variables for them just like what you did for NAME_REQUIRED_MESSAGE and etc.

Look forward to reviewing more of your domain logic in the next review.

Please approve and merge the PR once the team has read the feedback. Thanks!