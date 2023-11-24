import { useRef, useState } from 'react';



const CreatePost = () => {


  const imageRef = useRef(null); // useRef to store the image element
  const [imageUrl, setImageUrl] = useState(''); // useState to store the image URL

  const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
    const selectedFile: File | null = event.target.files[0];
  
    if (selectedFile) {
      const reader: FileReader = new FileReader();
  
      reader.onload = (event: ProgressEvent<FileReader>): void => {
        const imageData: string = event.target.result as string;
  
        setImageUrl(imageData); // Update the image URL
      };
  
      reader.readAsDataURL(selectedFile);
    }
  };
  return (
    <div className="create" id="newPost">
        <div className="headline">
          <h2>Add new post</h2>
        </div>
        <form className="form-post" method="get">
            <h4>Caption</h4>
            <textarea  name="newPost" placeholder="What's on your mind" ></textarea>
            <h4>Attachement</h4>
            <div className="file-input">
              {!imageUrl &&(<div className="input-container">

                <input type="file" id="imageInput" ref={imageRef} onChange={handleImageChange} />
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
