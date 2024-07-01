function showAllPosts() {
  $.ajax({
    type: 'GET',
    url: '//localhost:8080/api/posts',
    success: (data) => {
      let content = '';
      let localHost = 'http://127.0.0.1:5500/';
      let imageAbsolutePath = 'storage/images/';
      //TODO: Check the absolute path again ???

      const dummyObjectWhenCategoryNull = { name: 'khong co' };
      for (let index = 0; index < data.length; index++) {
        if (data[index].category === null)
          data[index].category = dummyObjectWhenCategoryNull;

        content += `      <div class="col col-md-12 col-sm-12 d-flex align-items-center justify-content-center" id="posts">
                <div class="card">
                <img src="${localHost}${imageAbsolutePath}${data[index].imageFileName}" class="card-img-top mx-auto img-fluid" alt="..." id="post-image">
                    <div class="card-body">
                        <h5 class="card-title">${data[index].title}</h5>  
                        <p class="card-text">${data[index].content}</p>
                        <p class="card-text">${data[index].shortDescription}</p>
                        <p class="card-text">${data[index].category.name}</p>
                           <p>
                                <a class="btn btn-primary btn-lg btn-block  w-100" href="#"><i class="fa-solid fa-pen-to-square"></i></a>
                            </p>
                    </div>
                </div>
            </div> `;
      }
      document.getElementById('posts').innerHTML = content;
    },
  });
}

showAllPosts();
