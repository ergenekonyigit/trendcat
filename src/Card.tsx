import 'twin.macro'

function Card() {
  return (
    <div tw="min-h-screen bg-gray-100 py-6 flex flex-col justify-center sm:py-12">
      <div tw="relative py-3 sm:max-w-xl sm:mx-auto">
        <div tw="absolute inset-0 bg-gradient-to-r from-blue-400 to-green-500 shadow-lg transform -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl" />
        <div tw="relative px-4 py-10 bg-white shadow-lg sm:rounded-3xl sm:p-20">
          <div tw="max-w-md mx-auto">
            <div tw="divide-y divide-gray-200">
              <div tw="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7">
                <p>
                  An advanced online playground for Tailwind CSS, including
                  support for things like:
                </p>
                <ul tw="list-disc space-y-2">
                  <li tw="flex items-start">
                    <span tw="h-6 flex items-center sm:h-7">
                      <svg
                        tw="flex-shrink-0 h-5 w-5 text-blue-500"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                      >
                        <path
                          fillRule="evenodd"
                          d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                          clipRule="evenodd"
                        />
                      </svg>
                    </span>
                    <p tw="ml-2">
                      Customizing your{' '}
                      <code tw="text-sm font-bold text-gray-900">
                        tailwind.config.js
                      </code>{' '}
                      file
                    </p>
                  </li>
                  <li tw="flex items-start">
                    <span tw="h-6 flex items-center sm:h-7">
                      <svg
                        tw="flex-shrink-0 h-5 w-5 text-blue-500"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                      >
                        <path
                          fillRule="evenodd"
                          d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                          clipRule="evenodd"
                        />
                      </svg>
                    </span>
                    <p tw="ml-2">
                      Extracting classes with
                      <code tw="text-sm font-bold text-gray-900">@apply</code>
                    </p>
                  </li>
                  <li tw="flex items-start">
                    <span tw="h-6 flex items-center sm:h-7">
                      <svg
                        tw="flex-shrink-0 h-5 w-5 text-blue-500"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                      >
                        <path
                          fillRule="evenodd"
                          d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                          clipRule="evenodd"
                        />
                      </svg>
                    </span>
                    <p tw="ml-2">Code completion with instant preview</p>
                  </li>
                </ul>
                <p>
                  Perfect for learning how the framework works, prototyping a
                  new idea, or creating a demo to share online.
                </p>
              </div>
              <div tw="pt-6 text-base leading-6 font-bold sm:text-lg sm:leading-7">
                <p>Want to dig deeper into Tailwind?</p>
                <p>
                  <a
                    href="https://tailwindcss.com/docs"
                    tw="text-blue-600 hover:text-blue-700"
                  >
                    {' '}
                    Read the docs &rarr;{' '}
                  </a>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Card
