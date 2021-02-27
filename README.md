# Classroom Booking Management System
## First Commit (2020/10/03)
In the first submission, each person submits their personal information as a record and local connection test with gitlab.  

## Second Commit (2020/10/12)  
In this commit, we updated the final version of UserCase and created our own branch for each member.The Master branch will be used to submit what is determined after each daily meeting of the team.  
- Regular weekly group meetings
- Each person USES their own branch, updates the main branch and cleans up their own branch after the group meeting

## Third Commit (2020/10/27)
This is the last version of the usercase. In this submission, we made the following modifications:
- Actor is broken down into four actors
- The operation permission of each user is clearly limited, so that the whole system conforms to the use logic and meets the requirements of teachers (Requirements will be introduced in the next section)
- Each use case description is added to make the system use case explicit
- After completing the first part of the character, We will enter the second part of the requirement analysis stage

Detailed use case diagram introduction:  
1. The function of this system is mainly divided into three parts: arranging the class schedule for each class in the school (specifically, the class time, the classroom and the teacher), booking the test classroom for the teacher, and booking the temporary class meeting classroom for the class.
2. For the functional requirements of the first part, we designed that only administrators have access to the curriculum arrangement. All courses are arranged by the administrator.The administrator displays the weekly schedule in the arrangement interface, and then selects the courses to be added at each time coordinate. The system background will automatically assign an empty classroom for the courses.
3. For the temporary test appointment function, our group is set as only teachers can make appointment and cancel and other operations.In order to prevent the wrong cancellation, the system will record the id of the person who made the reservation at each appointment. Only when the id of the person who made the reservation is the same as the id of the person who made the reservation can the cancellation be made.
4. For functions such as appointment of class meeting, our group is set as only the monitor can operate for the class.This requires administrators to set a person as monitor when creating a person account.You also need to verify the ID when you cancel

## Forth commit (2020/11/12) analysis done
In this submission, we have completed the production of sequence diagram and class diagram, which are as follows: change,log in, record, register, retrieve password, view mine, cancel, booking, cancel recording. 
- The "Login" and the "Retrieve Password" apply to all users.Our group believes that all users should be able to log in and retrieve their passwords
- "Cancel Recording", "Record", "Register" and "Cancel" are applicable to administrators only.Our group believes that administrators have the highest authority in the whole system and can make appointments, modify and cancel classes and classrooms of any class.At the same time, we designed for all users' account password to be created by the administrator, which can prevent users from arbitrary creation and permission restrictions.
- "Booking" and "Cancel" are the special operations of monitor and teacher.This is different from "CancelRecording" and "CancelRecording".Since the operation of the monitor and the teacher is only limited to the temporary classroom appointment, the system only needs to record the time, classroom and purpose, and does not need the course name.Also, reservations have a lower priority than administrator reservations.


## Last commit (2020/12/11) implementation-done
In this submission, our group jointly submitted a project based on ECLIPE.This project was developed based on the previous three designs.It satisfies the basic functions of classroom reservation, classroom conflict detection and user login.The following is the detailed introduction of the software:
- Launch the software through LoginUI.The login box pops up in the upper left corner of the screen.The user enters his or her ID and password to log in.Four accounts are preset: 
Administrator: Account number: A-00000000 Password: 123 
Ordinary student: Account number: S-18372301 Password: 123 
Monitor: Account number: M-18372302 Password: 123 
Teacher: Account number: T-11111111 Password: 123 
- Administrators can enter the class number they want to view and arrange in the SELECT option in the upper left corner, and the class schedule information will be displayed automatically.
- Two classes 183723 and 183721 are preset in the database
- Teachers and administrators have basically the same rights
- When the monitor logs in, he can only check the class schedule.Unable to select other classes to view
- When ordinary students log in, they can only check the schedule of the class, but cannot modify it.
- When adding or modifying an operation, just click the corresponding position, and the system will pop up a dialog box.You need to enter a user name and password for permission verification when adding