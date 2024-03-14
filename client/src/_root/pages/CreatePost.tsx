import { useEffect, useRef, useState } from 'react';
import { MdOutlineAddPhotoAlternate } from "react-icons/md";

import {set, useForm} from 'react-hook-form'
import axios from 'axios';


const CreatePost = () => {


  const imageRef = useRef(null); // useRef to store the image element
  const [imageUrl, setImageUrl] = useState(''); // useState to store the image URL

  const { register, handleSubmit } = useForm();





  const [submitData, setSubmitData] = useState(null);

  useEffect(() => {
    if (submitData) {
      const username = localStorage.getItem("myUserName");
      const caption = submitData.caption;
      const imageUrl = submitData.image;
  
      const formData = new FormData();
      formData.append('content', caption);
      formData.append('username', username);
      formData.append('picture', imageUrl);
      

      console.log(formData);
      axios.post(`http://localhost:8080/user/post/create`, formData,  {
        
        headers: {
          Authorization: `Bearer ${localStorage.getItem("myToken")}`,
        },
      }).then((response) => {
        console.log(response);
        console.log("Post created successfully");
      }).catch((error) => {
        console.log(error);
      });
    }
  }, [submitData]);
  
  const submitForm = (data: any) => {
    setSubmitData(data);
  }


  

  return (
    <div className="create center content-area" id="newPost">
        <div className="headline">
          <h2>Add new post</h2>
        </div>
        <form className="form-post" onSubmit={handleSubmit(submitForm)} method="post">
            <h4>Caption</h4>
            <textarea  placeholder="What's on your mind"  {...register('caption')}required ></textarea>
            <h4>Attachement</h4>
            <div className="file-input">
              {!imageUrl &&(<div className="input-container">
                <div className="upload-area">
                  <MdOutlineAddPhotoAlternate className="upload-img"/>
                  <span>Upload an image</span>
                </div>
                <input type="file" id="imageInput" {...register('image')}required/>
              </div>)}
              {imageUrl && <img src={imageUrl} className='picture-imported' alt="Imported Image" ref={imageRef} />}
            </div>
            {imageUrl&&            <button className='canceler' onClick={()=>{setImageUrl('')}}> Remove Image </button>
}
            <button type="submit" className="btn-primary">Post</button>
        </form>
    </div>
  )
}

export default CreatePost
