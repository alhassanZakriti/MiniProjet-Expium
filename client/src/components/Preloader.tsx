// import React from 'react'
import preloader from '../assets/preloader.gif'


const Preloader = () => {
  return (
    <div className='preloader-container'>
        <img src={preloader} className='preloader' alt="preloader" />
    </div>
  )
}

export default Preloader
