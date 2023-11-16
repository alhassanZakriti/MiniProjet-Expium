import { Link } from 'react-router-dom'
import logo from '../assets/logo.png'

const Logo = (props : any) => {
  return (
    <Link to='/' className={props.style}>
      <img src={logo} alt="logo" />
      <span>{props.Name}</span>
    </Link>
  )
}

export default Logo
