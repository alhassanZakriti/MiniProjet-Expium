import { Link } from "react-router-dom"

const Greeting = (props:any) => {
  return (
    <div className="top-left">
        <Link className='onPhone-btn' to="/search">
            <svg  viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M22.6129 24.4758L14.2298 16.0927C13.5645 16.625 12.7994 17.0463 11.9345 17.3568C11.0696 17.6673 10.1492 17.8226 9.17339 17.8226C6.75605 17.8226 4.71041 16.9851 3.03646 15.3103C1.36251 13.6355 0.525088 11.5898 0.5242 9.17336C0.5242 6.75602 1.36162 4.71038 3.03646 3.03643C4.7113 1.36248 6.75694 0.525057 9.17339 0.52417C11.5907 0.52417 13.6364 1.36159 15.3103 3.03643C16.9843 4.71127 17.8217 6.75691 17.8226 9.17336C17.8226 10.1492 17.6673 11.0695 17.3569 11.9345C17.0464 12.7994 16.625 13.5645 16.0927 14.2298L24.4758 22.6129L22.6129 24.4758ZM9.17339 15.1613C10.8367 15.1613 12.2507 14.5789 13.4155 13.4141C14.5802 12.2494 15.1622 10.8358 15.1613 9.17336C15.1613 7.51006 14.5789 6.09602 13.4142 4.93127C12.2494 3.76651 10.8358 3.18457 9.17339 3.18546C7.51009 3.18546 6.09605 3.76784 4.9313 4.9326C3.76654 6.09736 3.1846 7.51094 3.18549 9.17336C3.18549 10.8367 3.76787 12.2507 4.93263 13.4155C6.09739 14.5802 7.51097 15.1622 9.17339 15.1613Z" />
            </svg>
        </Link>
        <h2>
            Hey, {props.MyName}!
        </h2>
        <Link to="/profile">
            <img src={props.MyPfp} alt="profile picture" />
        </Link>
        <Link to="/settings">
            <svg width="28" height="28" viewBox="0 0 36 36" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M18.0219 0.125C19.3676 0.139667 20.7077 0.2955 22.0222 0.588833C22.3019 0.651258 22.5551 0.799488 22.7464 1.01281C22.9377 1.22613 23.0575 1.49389 23.0892 1.77867L23.4009 4.57817C23.4449 4.97285 23.581 5.3517 23.7981 5.68423C24.0152 6.01675 24.3073 6.29367 24.651 6.4927C24.9947 6.69173 25.3802 6.80731 25.7767 6.83014C26.1732 6.85297 26.5695 6.78242 26.9337 6.62417L29.5004 5.49667C29.7612 5.38181 30.0512 5.35081 30.3304 5.40796C30.6096 5.46511 30.8641 5.60757 31.0587 5.81567C32.9146 7.79752 34.2967 10.1745 35.1012 12.7677C35.1853 13.0404 35.1825 13.3325 35.0932 13.6035C35.0038 13.8745 34.8324 14.111 34.6026 14.2802L32.3274 15.9595C32.0069 16.1945 31.7462 16.5017 31.5665 16.8562C31.3868 17.2107 31.2932 17.6026 31.2932 18C31.2932 18.3975 31.3868 18.7893 31.5665 19.1438C31.7462 19.4983 32.0069 19.8055 32.3274 20.0405L34.6062 21.718C34.8364 21.8873 35.0081 22.1241 35.0975 22.3955C35.1868 22.6669 35.1894 22.9594 35.1049 23.2323C34.3007 25.8252 32.9192 28.2021 31.0642 30.1843C30.8699 30.3923 30.6158 30.5349 30.337 30.5924C30.0582 30.6499 29.7685 30.6194 29.5077 30.5052L26.9301 29.374C26.5663 29.2145 26.1701 29.1429 25.7736 29.165C25.377 29.187 24.9912 29.3021 24.6474 29.501C24.3036 29.6999 24.0114 29.9769 23.7945 30.3096C23.5777 30.6424 23.4422 31.0215 23.3991 31.4163L23.0874 34.214C23.0563 34.4954 22.939 34.7604 22.7517 34.9727C22.5643 35.185 22.316 35.3343 22.0406 35.4002C19.3851 36.032 16.6184 36.032 13.9629 35.4002C13.6872 35.3346 13.4384 35.1855 13.2507 34.9732C13.063 34.7608 12.9455 34.4957 12.9142 34.214L12.6044 31.42C12.5594 31.0264 12.4227 30.6488 12.2054 30.3176C11.988 29.9864 11.696 29.7108 11.3528 29.5129C11.0096 29.315 10.6248 29.2004 10.2292 29.1781C9.83368 29.1559 9.43844 29.2268 9.07524 29.385L6.49757 30.5143C6.23661 30.629 5.94647 30.6597 5.66729 30.6022C5.38811 30.5447 5.1337 30.4019 4.93924 30.1935C3.08403 28.209 1.70312 25.8295 0.900407 23.2342C0.815884 22.9612 0.818487 22.6687 0.907855 22.3973C0.997223 22.126 1.16892 21.8891 1.39907 21.7198L3.67791 20.0405C3.99846 19.8055 4.25914 19.4983 4.43883 19.1438C4.61852 18.7893 4.71216 18.3975 4.71216 18C4.71216 17.6026 4.61852 17.2107 4.43883 16.8562C4.25914 16.5017 3.99846 16.1945 3.67791 15.9595L1.39907 14.2838C1.16892 14.1145 0.997223 13.8777 0.907855 13.6063C0.818487 13.3349 0.815884 13.0424 0.900407 12.7695C1.7049 10.1763 3.08707 7.79935 4.94291 5.8175C5.13758 5.60941 5.3921 5.46694 5.67126 5.40979C5.95043 5.35264 6.24046 5.38364 6.50124 5.4985L9.06791 6.626C9.43278 6.78416 9.82965 6.85455 10.2267 6.83149C10.6237 6.80844 11.0097 6.69259 11.3538 6.49326C11.6979 6.29392 11.9905 6.01666 12.208 5.68374C12.4255 5.35082 12.5619 4.97153 12.6062 4.57633L12.9179 1.77867C12.9494 1.49332 13.0694 1.22497 13.261 1.01126C13.4527 0.797549 13.7065 0.649194 13.9867 0.587C15.3122 0.294057 16.6645 0.13923 18.0219 0.125ZM17.9999 12.5C16.5412 12.5 15.1423 13.0795 14.1108 14.1109C13.0794 15.1424 12.4999 16.5413 12.4999 18C12.4999 19.4587 13.0794 20.8576 14.1108 21.8891C15.1423 22.9205 16.5412 23.5 17.9999 23.5C19.4586 23.5 20.8575 22.9205 21.889 21.8891C22.9204 20.8576 23.4999 19.4587 23.4999 18C23.4999 16.5413 22.9204 15.1424 21.889 14.1109C20.8575 13.0795 19.4586 12.5 17.9999 12.5Z" fill="#363636"/>
            </svg>
        </Link>

    </div>
  )
}

export default Greeting
