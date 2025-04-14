import styled from 'styled-components';

const Wrapper = styled.nav`
  height: 6rem;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 1px 0 0 rgba(0, 0, 0, 0.1);
  background: var(--background-secondary-color);
  .nav-center {
    display: flex;
    width: 90vw;
    align-items: center;
    justify-content: space-between;
  }
  .toggle-btn {
    background: transparent;
    border: none;
    font-size: 1.75rem;
    color: var(--primary-500);
    cursor: pointer;
    display: flex;
    align-items: center;
    padding: 0.5rem;
    transition: var(--transition);
    border-radius: 0.5rem;
  }
  .toggle-btn:hover {
    background: var(--primary-100);
    color: var(--primary-700);
  }
  .logo-container {
    display: flex;
    align-items: center;
    gap: 1rem;
  }
  .logo-text {
    display: none;
  }
  .logo {
    display: flex;
    align-items: center;
    width: 100px;
  }
  .btn-container {
    display: flex;
    align-items: center;
    gap: 1rem;
  }
  @media (min-width: 992px) {
    position: sticky;
    top: 0;
    z-index: 100;
    .nav-center {
      width: 90%;
    }
    .logo {
      display: none;
    }
    .logo-text {
      display: block;
      margin: 0;
      text-transform: capitalize;
      color: var(--text-color);
      letter-spacing: 1px;
    }
  }
`;
export default Wrapper;
