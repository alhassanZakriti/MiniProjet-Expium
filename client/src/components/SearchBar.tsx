import {useForm} from 'react-hook-form'
// import { Form } from 'react-router-dom'

const SearchBar = () => {

    const { register, handleSubmit } = useForm()

    const submitForm = (data:any) => {
      console.log(data.Search)
    }
    
    
  return (
    <div>
      <form className='search-bar' onSubmit={handleSubmit(submitForm)}>
        <input type="text" className="input-search" placeholder="Looking for someone?" {...register('Search')}required/>
        <button className='search-btn' type="submit">
            <svg  viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M22.6129 24.4758L14.2298 16.0927C13.5645 16.625 12.7994 17.0463 11.9345 17.3568C11.0696 17.6673 10.1492 17.8226 9.17339 17.8226C6.75605 17.8226 4.71041 16.9851 3.03646 15.3103C1.36251 13.6355 0.525088 11.5898 0.5242 9.17336C0.5242 6.75602 1.36162 4.71038 3.03646 3.03643C4.7113 1.36248 6.75694 0.525057 9.17339 0.52417C11.5907 0.52417 13.6364 1.36159 15.3103 3.03643C16.9843 4.71127 17.8217 6.75691 17.8226 9.17336C17.8226 10.1492 17.6673 11.0695 17.3569 11.9345C17.0464 12.7994 16.625 13.5645 16.0927 14.2298L24.4758 22.6129L22.6129 24.4758ZM9.17339 15.1613C10.8367 15.1613 12.2507 14.5789 13.4155 13.4141C14.5802 12.2494 15.1622 10.8358 15.1613 9.17336C15.1613 7.51006 14.5789 6.09602 13.4142 4.93127C12.2494 3.76651 10.8358 3.18457 9.17339 3.18546C7.51009 3.18546 6.09605 3.76784 4.9313 4.9326C3.76654 6.09736 3.1846 7.51094 3.18549 9.17336C3.18549 10.8367 3.76787 12.2507 4.93263 13.4155C6.09739 14.5802 7.51097 15.1622 9.17339 15.1613Z" />
            </svg>

        </button>
      </form>
    </div>
  )
}

export default SearchBar
