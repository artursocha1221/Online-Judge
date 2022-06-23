Online-Judge gives you possibility of holding as well as participating in algorithmic contests.

List of endpoints you can use:

POST /user request body should contain JSON with nickname, email and role of a new user. Role can be either 'admin' or 'participant'. Example: {"nickname": "artur", "email": "artur@gmail.com", "role": "participant"}

POST /problem request body should contain JSON with statement and userID. Example: {"statement": "There is given integer n and integer sequence consists of n integers. Print the sequence in reverse order.", "userId": 1}

POST /test request body should contain JSON with input, output, problemID and userID. Example: {"input": "2 1 2", "output": "2 1", "userId": 1, "problemId": 1}
