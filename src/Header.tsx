import React from 'react'
import { Header, Heading, ActionButton, Flex } from '@adobe/react-spectrum'
import Rail from '@spectrum-icons/workflow/Rail'
function Head() {
  return (
    <Header gridArea="header">
      <Flex justifyContent="space-between" alignItems="center">
        <Heading level={1} margin="0px" height="40px">
          Trendcat
        </Heading>
        <ActionButton aria-label="Icon only" isQuiet>
          <Rail />
        </ActionButton>
      </Flex>
    </Header>
  )
}

export default Head
