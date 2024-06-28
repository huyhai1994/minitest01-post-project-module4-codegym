function showAllPosts() {
  $.ajax({
    type: 'GET',
    url: '//localhost:8080/api/posts',
    success: function (data) {
      let content = '';
      const dummyObjectWhenCategoryNull = { name: 'khong co' };
      for (let index = 0; index < data.length; index++) {
        if (data[index].category === null)
          data[index].category = dummyObjectWhenCategoryNull;
        content += `  <tr>
                <td>${index + 1}</td>
                <td>${data[index].title}</td>
                <td>${data[index].content}</td>
                <td>${data[index].shortDescription}</td>
                <td>${data[index].category.name}</td>
            </tr>`;
        console.log(data[index].category);
      }
      document.getElementById('posts').innerHTML = content;
    },
  });
}
showAllPosts();
