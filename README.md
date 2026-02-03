Title: Restricted File Sharing System
Summary:
Extend your previous assignments (Custom Authentication[Module-23] and Secure File Sharing Service[Module-22]) to build a secure, authenticated file-sharing system where files can be shared only with selected users, instead of being publicly accessible.

No UI is required — this is a backend-only, API-based project.

Requirements:
1.  You may reuse the custom authentication system from the previous assignment, including user registration with email and password, email verification using a unique link valid for 10 minutes, JWT-based authentication, and restriction that only verified users can log in.

2. Only authenticated and verified users are allowed to upload files.

3. You can use h2-database.

4. Files and related data should NOT be deleted. Each uploaded file must have a single shareable download link, and the same link must be used for all users. This is the exception with the previous assignment. Be careful.

5. Each file can be shared with multiple users, but only with users who are already registered and verified in the system.

6. Only users who are explicitly granted access to the file must be able to download it; all other users must receive an error response.

7. Proper validation, authorization checks, and exception handling are required.

8. Add api endpoint as you need. There is no restriction on it.
