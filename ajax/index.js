function showAllPosts() {
  $.ajax({
    type: 'GET',
    url: '//localhost:8080/api/posts',
    success: (data) => {
      let content = '';
      let localHost = 'http://127.0.0.1:5500/';
      let imageAbsolutePath =
        'Module4CodeGym/post-minitest-springmvc/storage/images/';

      const dummyObjectWhenCategoryNull = { name: 'khong co' };
      for (let index = 0; index < data.length; index++) {
        if (data[index].category === null)
          data[index].category = dummyObjectWhenCategoryNull;
        console.log(data[index].imageFileName);
        content += `  <tr>
                <td>${index + 1}</td>
                <td>${data[index].title}</td>
                <td>${data[index].content}</td>
                <td>${data[index].shortDescription}</td>
                <td>${data[index].category.name}</td>
                <td><img src=${localHost}${imageAbsolutePath}${
          data[index].imageFileName
        } width="100px" alt=""></td>
            </tr>`;
        console.log(data[index].category);
      }
      document.getElementById('posts').innerHTML = content;
    },
  });
}
showAllPosts();
