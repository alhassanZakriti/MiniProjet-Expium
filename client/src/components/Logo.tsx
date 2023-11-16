import logo from '../assets/logo.png'

const Logo = (props : any) => {
  return (
    <div className={props.style}>
      <img src={logo} alt="logo" />
      <span>{props.Name}</span>
    </div>
  )
}

export default Logo
