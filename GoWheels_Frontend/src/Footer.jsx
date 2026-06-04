import "./Footer.css"

const Footer = () => {
  return (
    <footer className="site-footer">
      <div className="footer-content">
        &copy; {new Date().getFullYear()} GOWheels. All rights reserved.
      </div>
    </footer>
  );
};

export default Footer;