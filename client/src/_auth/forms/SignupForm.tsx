import Logo from "../../components/Logo"
import {useState} from "react"
import {useForm} from 'react-hook-form'
import { Link } from "react-router-dom"





const SignupForm = () => {
  const { register, handleSubmit } = useForm()

  const submitForm = (data:any) => {
    console.log(data.userName)
  }
  const [isVisible,setIsVisible] = useState(false)

  return (
    <div className="hero-left">
      <Logo style='vertical-logo' Name='Expium'/>
      <h1 className="form-title">
        Create your account
      </h1>
      <p className="form-subtitle">
        Please enter the required informations
      </p>
      <form onSubmit={handleSubmit(submitForm)} className="auth-form">
        <input type="text" className="input-area" placeholder="First Name" {...register('firstName')}required/>
        <input type="text" className="input-area" placeholder="Last Name" {...register('lastName')}required/>
        <input type="text" className="input-area" placeholder="Username" {...register('userName')}required/>
        <input type="email" className="input-area" placeholder="Email" {...register('email')}required/>
        <div className="input-area">
          <input type={isVisible? "password": "text"} className="pw" placeholder="Password" {...register('password')}required/>
          <a onClick={()=>setIsVisible(!isVisible)}>
            {isVisible? <svg xmlns="http://www.w3.org/2000/svg" width="19"  viewBox="0 0 22 16" fill="none">
              <path d="M11 5C10.2044 5 9.44129 5.31607 8.87868 5.87868C8.31607 6.44129 8 7.20435 8 8C8 8.79565 8.31607 9.55871 8.87868 10.1213C9.44129 10.6839 10.2044 11 11 11C11.7956 11 12.5587 10.6839 13.1213 10.1213C13.6839 9.55871 14 8.79565 14 8C14 7.20435 13.6839 6.44129 13.1213 5.87868C12.5587 5.31607 11.7956 5 11 5ZM11 13C9.67392 13 8.40215 12.4732 7.46447 11.5355C6.52678 10.5979 6 9.32608 6 8C6 6.67392 6.52678 5.40215 7.46447 4.46447C8.40215 3.52678 9.67392 3 11 3C12.3261 3 13.5979 3.52678 14.5355 4.46447C15.4732 5.40215 16 6.67392 16 8C16 9.32608 15.4732 10.5979 14.5355 11.5355C13.5979 12.4732 12.3261 13 11 13ZM11 0.5C6 0.5 1.73 3.61 0 8C1.73 12.39 6 15.5 11 15.5C16 15.5 20.27 12.39 22 8C20.27 3.61 16 0.5 11 0.5Z" fill="#B0B0B0"/>
              </svg>: <svg width="20" height="18" viewBox="0 0 20 18" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd" clip-rule="evenodd" d="M18.53 1.53003C18.6625 1.38785 18.7346 1.19981 18.7311 1.00551C18.7277 0.811206 18.649 0.625821 18.5116 0.488408C18.3742 0.350995 18.1888 0.272283 17.9945 0.268855C17.8002 0.265426 17.6121 0.33755 17.47 0.47003L1.46997 16.47C1.39628 16.5387 1.33718 16.6215 1.29619 16.7135C1.2552 16.8055 1.23316 16.9048 1.23138 17.0055C1.2296 17.1062 1.24813 17.2062 1.28585 17.2996C1.32357 17.393 1.37971 17.4778 1.45093 17.5491C1.52215 17.6203 1.60698 17.6764 1.70037 17.7142C1.79376 17.7519 1.89379 17.7704 1.99449 17.7686C2.0952 17.7668 2.19451 17.7448 2.28651 17.7038C2.37851 17.6628 2.46131 17.6037 2.52997 17.53L5.56497 14.495C6.88297 15.103 8.39197 15.5 9.99997 15.5C12.618 15.5 14.972 14.449 16.668 13.147C17.518 12.495 18.215 11.771 18.703 11.067C19.183 10.375 19.5 9.64903 19.5 9.00003C19.5 8.35103 19.183 7.62503 18.703 6.93403C18.215 6.22903 17.518 5.50503 16.668 4.85403C16.398 4.64603 16.11 4.44403 15.808 4.25303L18.53 1.53003ZM13.13 6.93203L12.03 8.03003C12.2305 8.44989 12.2959 8.92159 12.2172 9.38018C12.1386 9.83877 11.9196 10.2617 11.5906 10.5907C11.2616 10.9197 10.8387 11.1386 10.3801 11.2173C9.92153 11.296 9.44983 11.2306 9.02997 11.03L7.92997 12.13C8.6511 12.6075 9.51511 12.821 10.3756 12.7344C11.2362 12.6479 12.0403 12.2665 12.6519 11.6549C13.2634 11.0434 13.6448 10.2392 13.7314 9.37869C13.818 8.51817 13.6044 7.65416 13.127 6.93303L13.13 6.93203Z" fill="#B0B0B0"/>
              <path d="M10.67 5.31C10.7111 5.31771 10.7535 5.31537 10.7936 5.30318C10.8336 5.29099 10.8701 5.26932 10.9 5.24L12.85 3.29C12.8803 3.26019 12.9023 3.22294 12.9137 3.18198C12.9251 3.14102 12.9256 3.09779 12.915 3.05659C12.9045 3.0154 12.8834 2.97769 12.8537 2.94722C12.824 2.91676 12.7869 2.89462 12.746 2.883C11.8523 2.63112 10.9285 2.50226 10 2.5C7.382 2.5 5.028 3.551 3.332 4.853C2.482 5.505 1.785 6.229 1.296 6.933C0.816 7.625 0.5 8.351 0.5 9C0.5 9.649 0.817 10.375 1.296 11.066C1.77186 11.7339 2.33408 12.3358 2.968 12.856C3.01544 12.8952 3.07584 12.9152 3.13731 12.9121C3.19877 12.9089 3.25682 12.8828 3.3 12.839L6.24 9.899C6.26932 9.86913 6.29099 9.83262 6.30318 9.79258C6.31537 9.75253 6.31771 9.71014 6.31 9.669C6.20163 9.07261 6.23945 8.45884 6.42022 7.88026C6.60099 7.30169 6.91932 6.77555 7.34794 6.34694C7.77655 5.91832 8.30269 5.59999 8.88126 5.41922C9.45984 5.23845 10.0736 5.20063 10.67 5.309V5.31Z" fill="#B0B0B0"/>
              </svg>
            }
          </a>
        </div>
        
        <button className="primary-btn" type="submit">Sign up</button>
        <p className="text">You already have an account? <Link to="/sign-in" className="primary-text">Sign in</Link></p>
      </form>
    </div>
  )
}

export default SignupForm
