# Online-Judge

## This application gives you possibility of holding as well as participating in algorithmic contests.

List of endpoints you can use:

- POST /user request body should contain JSON with nickname, email and role of a new user. Role can be either 'admin' or 'participant'.
Example: `{"nickname": "artur", "email": "artur@gmail.com", "role": "participant"}`


- POST /problem request body should contain JSON with problem statement and user id.
Example: `{"statement": "There is given integer n and sequence consists of n integers. Print the sequence in reverse order.", "userId": 1}`

- POST /test request body should contain JSON with input, output, problem id and user id.
Example: `{"input": "2 1 2", "output": "2 1", "problemId": 1, "userId": 1}`

- POST /solution request body should contain JSON with source code, problem id, user id and code language. Language can be either 'cpp' or 'java'.
Example: `{"code": "#include <iostream>#include <vector>#include <algorithm>\r\nusing namespace std;int main(){int n;cin >> n;vector<int> a(n);for (int i = 0; i < n; ++i)cin >> a[i];reverse(a.begin(), a.end());for (int i = 0; i < n; ++i)cout << a[i] << endl;return 0;}", "problemID": 1, "userId": 1, "language": "cpp"}`
