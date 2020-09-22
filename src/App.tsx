import React from 'react'
import {
  Provider,
  defaultTheme,
  Grid,
  View,
  Flex,
  repeat,
  minmax
} from '@adobe/react-spectrum'
import Header from './Header'

function App() {
  // '1(1)'
  // '4(1+1+1+1)'
  // '4(1+2+1)'
  // '4(2+1+1)'
  // '4(1+1+2)'
  // '4(2+2)'
  // '4(3+1)'
  // '4(1+3)'

  return (
    <Provider theme={defaultTheme}>
      <Flex width="100%" justifyContent="center">
        <Grid
          areas={[
            'top top top top top top',
            'left header header header header right',
            'left col1 col2 col3 col4 right',
            'bottom bottom bottom bottom bottom bottom'
          ]}
          columns={[0, repeat(4, minmax('300px', '1fr')), 0]}
          rows={[0, 'size-500', 'auto', 0]}
          gap="size-200"
          height="100vh"
        >
          <Header />
          <View backgroundColor="gray-600" gridArea="col1" overflow="scroll" />
          <View backgroundColor="gray-600" gridArea="col2" />
          <View backgroundColor="gray-600" gridArea="col3" />
          <View backgroundColor="gray-600" gridArea="col4" />
        </Grid>
      </Flex>
    </Provider>
  )
}

export default App
