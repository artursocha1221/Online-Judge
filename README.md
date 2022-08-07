# Online-Judge

## This application gives you possibility of holding as well as participating in algorithmic contests.

List of endpoints you can use:

- `POST /user`
This request creates a new user.
Request body should contain JSON with nickname, email and role of a new user.
Role can be either 'admin' or 'participant'.
Example: `{"nickname": "artur", "email": "artur@gmail.com", "role": "participant"}`

- `POST /problem`
This request adds a new problem.
Request body should contain JSON with problem statement and user id.
The request can be used only by admin.
Example: `{"statement": "There is given integer n and sequence consists of n integers. Print the sequence in reverse order.", "userId": 1}`

- `POST /test`
This request adds a new test to some particular problem and judges all solutions for this problem on this test.
Request body should contain JSON with input, output, problem id and user id.
This request can be used only by admin.
Example: `{"input": "2 1 2", "output": "2 1", "problemId": 1, "userId": 1}`

- `POST /solution`
This request sends a solution and judges this solution on all its tests.
Request body should contain JSON with source code, problem id, user id and code language.
Language can be either 'cpp' or 'java'.
This request can be used only by participant.
As a result you will receive an information whether your solution passed all tests or not.
Example: `{"code": "#include <iostream>#include <vector>#include <algorithm>\r\nusing namespace std;int main(){int n;cin >> n;vector<int> a(n);for (int i = 0; i < n; ++i)cin >> a[i];reverse(a.begin(), a.end());for (int i = 0; i < n; ++i)cout << a[i] << endl;return 0;}", "problemID": 1, "userId": 1, "language": "cpp"}`

- `POST /friend`
This request adds a new friend to some particular user.
Request body should contain JSON with user id and friend id.
This request can be used only by participant. Friend also has to be participant.
Example: `{"userId": 2, "friendId": 7}`

============================================================================================

- `GET /scoreboard`
This request gives you a list of JSONs with user rank, user id and number of problems solved by the user. This list is sorted by number of problems solved by the user. In case of a tie all participants with equal number of solved problems will be returned ascending by their id.

- `GET /scoreboard/userId?friendsOnly=true`
This request gives you a list of JSONs with user rank, user id and number of problems solved by the user.
This list contains only friends of userId and is sorted by number of problems solved by the user. In case of a tie all participants with equal number of solved problems will be returned ascending by their id.
This request can be used only by participant.

- `GET /user/userId`
This request gives you a list of JSONs with nickname, email, role and information whether or not the user cheated.
This request can be used only by admin.

- `GET /problem`
This request gives you a list of JSONs with problem statements.

- `GET /test/problemId/userId`
This request gives you a list of JSONs with input and output of tests to problemId.
This request can be used only by admin.

- `GET /solution/userId`
This request gives you a list of JSONs with code, problemId, userId, language and results.
If userId is admin all solutions will be returned. Otherwise, there will be returned solutions sent by userId.

If two different participants send the same solution to the same problem they both will be disqualified from the contest and removed from the scoreboard.
