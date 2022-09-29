<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
</head>
<body>

	<tr>
		<th>내용</th>
		<td>
			<textarea id="content" name="food_content"></textarea>
			<script type="text/javascript">	// 글쓰기 editor 및 사진 업로드 기능
				CKEDITOR.replace('content',
				{filebrowserUploadUrl:'/food/imageUpload.do'
				});
			</script>
		</td>
	</tr>

</body>
</html>