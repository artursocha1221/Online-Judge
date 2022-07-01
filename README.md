# Online-Judge

## This application gives you possibility of holding as well as participating in algorithmic contests.

List of endpoints you can use:

- POST /user
This request creates new user.
Request body should contain JSON with nickname, email and role of a new user.
Role can be either 'admin' or 'participant'.
Example: `{"nickname": "artur", "email": "artur@gmail.com", "role": "participant"}`

- POST /problem
This request adds a new problem.
Request body should contain JSON with problem statement and user id.
The request can be used only by admin.
Example: `{"statement": "There is given integer n and sequence consists of n integers. Print the sequence in reverse order.", "userId": 1}`

- POST /test
This request adds new test to some particular problem and judges all solutions for this problem on this test.
Request body should contain JSON with input, output, problem id and user id.
This request can be used only by admin.
Example: `{"input": "2 1 2", "output": "2 1", "problemId": 1, "userId": 1}`

- POST /solution
This request sends solution and judges this solution on all its tests.
Request body should contain JSON with source code, problem id, user id and code language.
Language can be either 'cpp' or 'java'.
This request can be used only by participant.
Example: `{"code": "#include <iostream>#include <vector>#include <algorithm>\r\nusing namespace std;int main(){int n;cin >> n;vector<int> a(n);for (int i = 0; i < n; ++i)cin >> a[i];reverse(a.begin(), a.end());for (int i = 0; i < n; ++i)cout << a[i] << endl;return 0;}", "problemID": 1, "userId": 1, "language": "cpp"}`

If two different participants send the same solution to the same problem they both will be disqualified from the contest and removed from the scoreboard.
