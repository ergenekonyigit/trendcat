import { render, screen } from '@testing-library/react'
import App from './App'

test('renders Read the docs link', () => {
  render(<App />)
  const linkElement = screen.getByText(/Read the docs/i)
  expect(linkElement).toBeInTheDocument()
})
