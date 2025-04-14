import styled from "styled-components";

const Wrapper = styled.nav`
  padding: 0 2rem;
  
  .nav-links {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    margin-top: 2rem;
  }
  
  .nav-link {
    display: flex;
    align-items: center;
    color: var(--grey-500);
    padding: 1rem;
    text-transform: capitalize;
    transition: var(--transition);
    border-radius: 0.5rem;
    width: 100%;
    text-decoration: none;
  }
  
  .nav-link:hover {
    background: var(--grey-100);
    color: var(--grey-900);
    padding-left: 2rem;
  }
  
  .nav-link.active {
    background: var(--grey-100);
    color: var(--primary-500);
  }
  
  .icon {
    font-size: 1.5rem;
    margin-right: 1rem;
    display: grid;
    place-items: center;
  }
  
  @media (max-width: 992px) {
    padding: 0 1rem;
    
    .nav-link {
      padding: 0.75rem;
    }
    
    .nav-link:hover {
      padding-left: 1.5rem;
    }
  }
`;

export default Wrapper; 