<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 회원가입 </title>
</head>
<body>
	<br />
	<h1 align="center"> 회원가입 </h1>
	<form action="/test/signup" method="post">
		<table>
			<tr>
				<td>아이디 *</td>
				<td><input type="text" name="id" id="id" /></td>
			</tr>
			<tr>
				<td>아이디 사용가능 여부</td>
				<td><input type="text" id="checkResult" disabled /></td>
			</tr>
			<tr>
				<td>비밀번호 *</td>
				<td><input type="password" name="pw" /></td>
			</tr>
			<tr>
				<td>비밀번호 확인 *</td>
				<td><input type="password" name="pwch" /></td>
			</tr>
			<tr>
				<td>이름 *</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>email</td>
				<td>
					<input type="text" name="email" />
				</td>
			</tr>			
			<tr>
				<td>성별 </td>
				<td>
					남 <input type="radio" name="gender" value="male" checked />
					여 <input type="radio" name="gender" value="female" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="회원 가입" />
					<input type="reset" value="재작성" />
					<input type="button" value="취소" onclick="window.location='/test/main'"/>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>